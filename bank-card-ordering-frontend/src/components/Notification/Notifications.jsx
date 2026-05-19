import React, { useEffect, useState } from "react";
import axios from "axios";
import { IoNotificationsOutline } from "react-icons/io5";
import "./Notifications.css";

export default function Notifications() {
  const [alerts, setAlerts] = useState([]);
  const [open, setOpen] = useState(false);

  const customerId = localStorage.getItem("customerId");

  useEffect(() => {
    fetchAlerts();
  }, []);

  const fetchAlerts = () => {
    axios
      .get(`http://localhost:8080/api/notifications/${customerId}`)
      .then((res) => setAlerts(res.data))
      .catch((err) => console.log(err));
  };

  return (
    <div className="notif-container">
      <div className="notif-icon-wrapper" onClick={() => setOpen(!open)}>
        <IoNotificationsOutline className="notif-icon" />

        {alerts.length > 0 && (
          <span className="notif-badge">{alerts.length}</span>
        )}
      </div>

      {open && (
        <div className="notif-dropdown">
          <h4 className="notif-title">Notifications</h4>

          {alerts.length === 0 ? (
            <p className="notif-empty">No notifications</p>
          ) : (
            alerts.map((alert, index) => (
              <div className="notif-card" key={index}>
                <p className="notif-text">{alert.message}</p>
                <p className="notif-days-left">{alert.createdAt}</p>
              </div>
            ))
          )}
        </div>
      )}
    </div>
  );
}