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
import axios from "axios";

export default function Header() {
  const navigate = useNavigate();
  const location = useLocation();

  const [unreadCount, setUnreadCount] = useState(0);
  const [notifications, setNotifications] = useState([]);
  const [dropdownOpen, setDropdownOpen] = useState(false);

  const userId = localStorage.getItem("customerId");
  const token = localStorage.getItem("token");

  const isLoginPage = location.pathname === "/";

  useEffect(() => {
    if (!userId) return;

    axios
      .get(`http://localhost:8080/api/notifications/${userId}`)
      .then((res) => {
        setNotifications(res.data);
        setUnreadCount(res.data.length);
      })
      .catch((err) => console.log(err));
  }, [userId]);

  useEffect(() => {
    if (!userId) return;

    const socket = new SockJS("http://localhost:8080/ws");

    const client = new Client({
      webSocketFactory: () => socket,
      reconnectDelay: 5000,
      debug: (str) => console.log(str),

      onConnect: () => {
        client.subscribe(`/topic/notifications/${userId}`, (msg) => {
          const data = JSON.parse(msg.body);

          setNotifications((prev) => [data, ...prev]);
          setUnreadCount((prev) => prev + 1);
        });
      },
    });

    client.activate();

    return () => client.deactivate();
  }, [userId]);

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("customerId");
    navigate("/");
  };

  const toggleDropdown = () => {
    setDropdownOpen(!dropdownOpen);
    setUnreadCount(0);
  };

  return (
    <div className="header-bar">
      <div className="logo">Bank Name</div>

      {!isLoginPage && token && (
        <div className="notification-area">
          <div className="notif-wrapper" onClick={toggleDropdown}>
            <IoNotificationsOutline size={26} className="notif-icon" />

            {unreadCount > 0 && (
              <span className="notif-badge">{unreadCount}</span>
            )}
          </div>
          {dropdownOpen && (
            <div className="notif-dropdown">
              <h4 className="notif-title">Notifications</h4>

              {notifications.length === 0 ? (
                <p className="notif-empty">No notifications</p>
              ) : (
                notifications.map((n, i) => (
                  <div key={i} className="notif-card">
                    <p className="notif-text">{n.message}</p>
                    <p className="notif-time">{n.time}</p>
                  </div>
                ))
              )}
            </div>
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