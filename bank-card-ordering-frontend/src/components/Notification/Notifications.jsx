import React, { useEffect, useState } from "react";
import axios from "axios";
import { IoNotificationsOutline } from "react-icons/io5";
import {
  connectWebSocket,
  disconnectWebSocket
} from "../../websocket";
import { toast } from "react-toastify";
import "./Notifications.css";

export default function Notifications() {
  const [alerts, setAlerts] = useState([]);
  const [open, setOpen] = useState(false);

  const customerId = localStorage.getItem("customerId");

  const fetchRecent = async () => {
    try {
      const res = await axios.get(
        `http://localhost:8080/api/notifications/recent-five/${customerId}`
      );

      setAlerts((res.data || []).slice(0, 5));
    } catch (err) {
      console.log("Notification fetch error:", err);
    }
  };

  useEffect(() => {
    if (!customerId) return;

    fetchRecent();

    connectWebSocket(customerId, (notification) => {
      toast.success(notification.message);

      setAlerts((prev) => {
        const filtered = prev.filter(
          (item) => item.id !== notification.id
        );

        return [notification, ...filtered].slice(0, 5);
      });
    });

    return () => {
      disconnectWebSocket();
    };
  }, [customerId]);

  const unreadCount = alerts.filter(
    (notification) => !notification.read
  ).length;

  return (
    <div className="notif-container">
      <div
        className="notif-icon-wrapper"
        onClick={() => setOpen(!open)}
      >
        <IoNotificationsOutline className="notif-icon" />

        {unreadCount > 0 && (
          <span className="notif-badge">
            {unreadCount}
          </span>
        )}
      </div>

      {open && (
        <div className="notif-dropdown">
          <div className="notif-title">
            Recent Notifications
          </div>

          {alerts.length === 0 ? (
            <p className="notif-empty">
              No notifications available
            </p>
          ) : (
            alerts.map((notification) => (
              <div
                className={`notif-card ${
                  !notification.read ? "unread" : ""
                }`}
                key={notification.id}
              >
                <p className="notif-heading">
                  {notification.title}
                </p>

                <p className="notif-message">
                  {notification.message}
                </p>

                <small className="notif-time">
                  {notification.updatedAt
                    ? new Date(
                        notification.updatedAt
                      ).toLocaleString()
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