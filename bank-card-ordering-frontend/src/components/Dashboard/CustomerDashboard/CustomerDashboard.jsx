import React, { useEffect, useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import MyActiveCards from "../../MyActiveCards/MyActiveCards";
import RecentCardTable from "../../RecentCardTable/RecentCardTable";
import "./CustomerDashboard.css";

export default function CustomerDashboard() {

  const [requests, setRequests] = useState([]);

  useEffect(() => {
    loadRequests();
  }, []);

  const loadRequests = async () => {
    const token = localStorage.getItem("token");

    const res = await axios.get(
      "http://localhost:8080/api/request-card/email",
      {
        headers: { Authorization: `Bearer ${token}` },
      }
    );

    setRequests(res.data);
  };

  const recentTwoRequests = [...requests]
    .sort((a, b) => new Date(b.updatedAt) - new Date(a.updatedAt))
    .slice(0, 2);

  return (
    <div className="customer-dashboard">

      <h2 className="title">Customer Dashboard</h2>
      <div className="top-boxes">
        <Link to="/dashboard/request-new-card" className="box">
          Request New Card
        </Link>
        <Link to="/dashboard/my-cards" className="box">
          My Cards
        </Link>
        <Link to="/dashboard/track-requests" className="box">
          Track Requests
        </Link>
      </div>
      <hr />
      <MyActiveCards />
      <hr />
      <h3 className="heading">Recent Requests</h3>
      <RecentCardTable requests={recentTwoRequests} />
    </div>
  );
}