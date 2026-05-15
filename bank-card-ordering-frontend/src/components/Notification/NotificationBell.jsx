import React, { useEffect, useState } from "react";
import axios from "axios";
import { IoNotificationsOutline } from "react-icons/io5";
import "./Notifications.css";

export default function Notifications() {
  const [alerts, setAlerts] = useState([]);
  const [open, setOpen] = useState(false);

  useEffect(() => {
    fetchAlerts();
    const interval = setInterval(fetchAlerts, 60000); // fetch every 1 min
    return () => clearInterval(interval);
  }, []);

  const fetchAlerts = () => {
    axios
      .get("http://localhost:8080/api/cards/expiry-alerts")
      .then((res) => setAlerts(res.data))
      .catch((err) => console.log(err));
  };

  return (
    <div className="notif-container">
      <div className="notif-icon-wrapper" onClick={() => setOpen(!open)}>
        <IoNotificationsOutline className="notif-icon" />
        {alerts.length > 0 && <span className="notif-badge">{alerts.length}</span>}
      </div>

      {open && (
        <div className="notif-dropdown">
          <h4 className="notif-title">Notifications</h4>

          {alerts.length === 0 ? (
            <p className="notif-empty">No notifications</p>
          ) : (
            alerts.map((alert, index) => (
              <div className="notif-card" key={index}>
                <p className="notif-text">
                  Your card <b>{alert.cardNumber}</b> will expire on <b>{alert.expiryDate}</b>
                </p>
                <p className="notif-days-left">{alert.daysLeft} days remaining</p>
              </div>
            ))
          )}
        </div>
      )}
    </div>
  );
}