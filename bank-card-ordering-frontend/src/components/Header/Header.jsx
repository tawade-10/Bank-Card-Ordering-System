// import React from "react";
// import "./Header.css";
// import { useNavigate, useLocation } from "react-router-dom";
// import { IoNotificationsOutline } from "react-icons/io5"; // bell icon
//
// export default function Header() {
//   const navigate = useNavigate();
//   const location = useLocation();
//
//   const handleLogout = () => {
//     localStorage.removeItem("token");
//     navigate("/");
//   };
//
//   const isLoginPage = location.pathname === "/";
//
//   const goToNotifications = () => {
//     navigate("/notifications");
//   };
//
//   return (
//     <div className="header-bar">
//       <div className="logo">Bank Name</div>
//
//       {/* 🔔 Clickable Bell Icon */}
//       {!isLoginPage && localStorage.getItem("token") && (
//         <div className="notif-icon" onClick={goToNotifications}>
//           <IoNotificationsOutline size={26} />
//         </div>
//       )}
//
//       {!isLoginPage && localStorage.getItem("token") && (
//         <button className="logout-btn" onClick={handleLogout}>
//           Logout
//         </button>
//       )}
//     </div>
//   );
// }

import React, { useEffect, useState } from "react";
import "./Header.css";
import { useNavigate, useLocation } from "react-router-dom";
import { IoNotificationsOutline } from "react-icons/io5";
import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";

export default function Header() {
  const navigate = useNavigate();
  const location = useLocation();
  const [unreadCount, setUnreadCount] = useState(0);
  const [stompClient, setStompClient] = useState(null);

  const userId = localStorage.getItem("customerId"); // store this after login
  const token = localStorage.getItem("token");

  const isLoginPage = location.pathname === "/";

  useEffect(() => {
   if (!userId) return;

   const socket = new SockJS("http://localhost:8080/ws");

   const client = new Client({
     webSocketFactory: () => socket,
     reconnectDelay: 5000,
     debug: (str) => console.log(str),
     onConnect: () => {
       client.subscribe(`/topic/notifications/${userId}`, () => {
         setUnreadCount((prev) => prev + 1);
       });
     }
   });

   client.activate();
   setStompClient(client);

   return () => client.deactivate();
 }, [userId]);

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("customerId");
    navigate("/");
  };

  const goToNotifications = () => {
    navigate("/notifications");
    setUnreadCount(0); // reset on view
  };

  return (
    <div className="header-bar">
      <div className="logo">Bank Name</div>

      {!isLoginPage && token && (
        <div className="notif-wrapper" onClick={goToNotifications}>
          <IoNotificationsOutline size={26} className="notif-icon" />

          {unreadCount > 0 && (
            <span className="notif-badge">{unreadCount}</span>
          )}
        </div>
      )}

      {!isLoginPage && token && (
        <button className="logout-btn" onClick={handleLogout}>
          Logout
        </button>
      )}
    </div>
  );
}