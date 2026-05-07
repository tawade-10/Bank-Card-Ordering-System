import React, { useEffect, useState } from "react";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";

export default function NotificationBell({ userId }) {
  const [notifications, setNotifications] = useState([]);
  const [open, setOpen] = useState(false);

  useEffect(() => {
    if (!userId) return;

    const socket = new SockJS("http://localhost:8080/ws");

    const client = new Client({
      webSocketFactory: () => socket,
      reconnectDelay: 5000, // auto reconnect

      onConnect: () => {
        console.log("🔔 Notification socket connected");

        client.subscribe(`/topic/notifications/${userId}`, (msg) => {
          const data = JSON.parse(msg.body);

          setNotifications((prev) => [data, ...prev]);
        });
      },
    });

    client.activate();

    return () => {
      console.log("🔌 Notification socket disconnected");
      client.deactivate();      // VERY IMPORTANT
    };
  }, [userId]);

  return (
    <div style={{ position: "relative" }}>
      {/* Bell Button */}
      <div
        onClick={() => setOpen(!open)}
        style={{
          cursor: "pointer",
          fontSize: "24px",
          position: "relative",
          userSelect: "none",
        }}
      >
        🔔
        {notifications.length > 0 && (
          <span
            style={{
              position: "absolute",
              top: -5,
              right: -8,
              background: "red",
              color: "white",
              borderRadius: "50%",
              padding: "2px 7px",
              fontSize: "12px",
              fontWeight: "bold",
            }}
          >
            {notifications.length}
          </span>
        )}
      </div>

      {/* Dropdown */}
      {open && (
        <div
          style={{
            position: "absolute",
            top: "30px",
            right: 0,
            width: "260px",
            background: "#fff",
            borderRadius: "8px",
            padding: "10px",
            boxShadow: "0 2px 10px rgba(0,0,0,0.15)",
            zIndex: 999,
          }}
        >
          <h4 style={{ marginBottom: "8px" }}>Notifications</h4>

          {notifications.length === 0 ? (
            <p>No notifications yet</p>
          ) : (
            notifications.map((n, i) => (
              <div
                key={i}
                style={{
                  padding: "8px 0",
                  borderBottom: "1px solid #eee",
                }}
              >
                <strong>{n.title}</strong>
                <p style={{ margin: 0, fontSize: "14px" }}>{n.message}</p>
              </div>
            ))
          )}
        </div>
      )}
    </div>
  );
}