import React, { useEffect, useState } from "react";
import SockJS from "sockjs-client";
import { over } from "stompjs";

export default function NotificationBell({ userId }) {
  const [stompClient, setStompClient] = useState(null);
  const [notifications, setNotifications] = useState([]);
  const [open, setOpen] = useState(false);

  useEffect(() => {
    const socket = new SockJS("http://localhost:8080/ws");
    const client = over(socket);

    client.connect({}, () => {
      client.subscribe(`/topic/notifications/${userId}`, (message) => {
        const notification = JSON.parse(message.body);
        setNotifications((prev) => [notification, ...prev]);
      });
    });

    setStompClient(client);
  }, [userId]);

  return (
    <div style={{ position: "relative" }}>
      <div
        onClick={() => setOpen(!open)}
        style={{ cursor: "pointer", fontSize: "22px" }}
      >
        🔔
        {notifications.length > 0 && (
          <span style={{
            position: "absolute",
            top: -5,
            right: -5,
            background: "red",
            borderRadius: "50%",
            padding: "3px 7px",
            color: "white",
            fontSize: "12px"
          }}>
            {notifications.length}
          </span>
        )}
      </div>

      {open && (
        <div style={{
          position: "absolute",
          top: "30px",
          right: 0,
          width: "250px",
          background: "white",
          color: "black",
          border: "1px solid #ccc",
          borderRadius: "5px",
          padding: "10px",
          zIndex: 1000
        }}>
          <h5>Notifications</h5>
          {notifications.length === 0 && <p>No notifications</p>}
          {notifications.map((n, index) => (
            <div key={index} style={{
              padding: "6px",
              borderBottom: "1px solid #eee"
            }}>
              <strong>{n.title}</strong>
              <p>{n.message}</p>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}