import React, { useEffect, useState, useRef } from "react";
import axios from "axios";
import { IoNotificationsOutline } from "react-icons/io5";
import { connectWebSocket } from "../../websocket";
import { toast } from "react-toastify";
import "./Notifications.css";

export default function Notifications() {
  const [alerts, setAlerts] = useState([]);
  const [open, setOpen] = useState(false);

  const customerId = localStorage.getItem("customerId");
  const socketRef = useRef(null);

  const fetchRecent = async () => {
    try {
      const res = await axios.get(
        `http://localhost:8080/api/notifications/recent-five/${customerId}`
      );

      setAlerts(res.data || []);
    } catch (err) {
      console.log("fetch error", err);
    }
  };

  useEffect(() => {
    if (!customerId) return;

    fetchRecent();

    socketRef.current = connectWebSocket(customerId, (n) => {
      toast.success(n.message);

      setAlerts((prev) => {
        const filtered = prev.filter((x) => x.id !== n.id);
        const updated = [n, ...filtered];
        return updated.slice(0, 5);
      });
    });

    return () => {
      socketRef.current?.deactivate?.();
    };
  }, [customerId]);

  return (
    <div className="notif-container">
      <div className="notif-icon-wrapper" onClick={() => setOpen(!open)}>
        <IoNotificationsOutline className="notif-icon" />

        {alerts.some((a) => !a.read) && (
          <span className="notif-badge">
            {alerts.filter((a) => !a.read).length}
          </span>
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
                <p className="notif-text">
                  <strong>{n.title}</strong>
                </p>
                <p className="notif-text">{n.message}</p>
                <small>
                  {n.updatedAt
                    ? new Date(n.updatedAt).toLocaleString()
                    : ""}
                </small>
              </div>
            ))
          )}
        </div>
      )}
    </div>
  );
}