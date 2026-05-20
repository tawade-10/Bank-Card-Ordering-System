import React, { useEffect, useState } from "react";
import axios from "axios";
import { IoNotificationsOutline } from "react-icons/io5";
import { connectWebSocket } from "../../websocket";
import "./Notifications.css";

export default function Notifications() {
  const [alerts, setAlerts] = useState([]);
  const [open, setOpen] = useState(false);

  const customerId = localStorage.getItem("customerId");

  useEffect(() => {
    if (!customerId) return;

    fetchAlerts();

    connectWebSocket(customerId, (newNotification) => {
      console.log("📩 New WebSocket notification:", newNotification);
      setAlerts((prev) => [newNotification, ...prev]);
    });

  }, [customerId]);

  const fetchAlerts = async () => {
    const res = await axios.get(`http://localhost:8080/api/notification/${customerId}`);
    setAlerts(res.data);
  };

  return (
    <div className="notification-wrapper">
      <IoNotificationsOutline
        size={28}
        className="notification-icon"
        onClick={() => setOpen(!open)}
      />

      {open && (
        <div className="notification-container">
          {alerts.length === 0 ? (
            <p>No notifications yet</p>
          ) : (
            alerts.map((n) => (
              <div className="notification-item" key={n.id}>
                <strong>{n.title}</strong>
                <p>{n.message}</p>
                <small>{new Date(n.createdAt).toLocaleString()}</small>
              </div>
            ))
          )}
        </div>
      )}
    </div>
  );
}