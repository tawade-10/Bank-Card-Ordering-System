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

      setAlerts((prev) => {
        const exists = prev.find((a) => a.id === newNotification.id);
        if (exists) {
          return prev.map((a) => (a.id === newNotification.id ? newNotification : a));
        }
        return [newNotification, ...prev];
      });
    });

  }, [customerId]);

  const fetchAlerts = async () => {
    const res = await axios.get(`http://localhost:8080/api/notification/${customerId}`);
    setAlerts(res.data);
  };

  return (
    <div className="notif-container">
      <div className="notif-icon-wrapper" onClick={() => setOpen(!open)}>
        <IoNotificationsOutline className="notif-icon" />

        {alerts.some((a) => !a.read) && (
          <span className="notif-badge">{alerts.filter((a) => !a.read).length}</span>
        )}
      </div>

      {open && (
        <div className="notif-dropdown">
          <div className="notif-title">Notifications</div>

          {alerts.length === 0 ? (
            <p className="notif-empty">No notifications yet</p>
          ) : (
            alerts.map((n) => (
              <div className="notif-card" key={n.id}>
                <p className="notif-text"><strong>{n.title}</strong></p>
                <p className="notif-text">{n.message}</p>
                <small>{new Date(n.updatedAt).toLocaleString()}</small>
              </div>
            ))
          )}
        </div>
      )}
    </div>
  );
}