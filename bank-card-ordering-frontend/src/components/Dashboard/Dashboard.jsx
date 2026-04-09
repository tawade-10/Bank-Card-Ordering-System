import React, { useEffect, useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import "./Dashboard.css";
import MyCards from "../MyCards/MyCards";

import RecentCardTable from "../RecentCardTable/RecentCardTable";

export default function Dashboard() {
  const [requests, setRequests] = useState([]);

  useEffect(() => {
    loadMyRequests();
  }, []);

  const loadMyRequests = async () => {
    try {
      const token = localStorage.getItem("token");

      const response = await axios.get(
        "http://localhost:8080/api/request-card",
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      setRequests(response.data);
    } catch (error) {
      console.error("Error fetching requests:", error);
      alert("Failed to fetch card requests.");
    }
  };

  return (
    <div className="dashboard-wrapper">
      <h2 className="dashboard-title">Dashboard</h2>

      {/* TOP BOXES */}
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

      <hr className="divider" />

      <h3 className="section-title">My Active Cards</h3>
      <MyCards/>

      <hr className="divider" />

      <h3 className="section-title">Recent Card Requests</h3>
      <RecentCardTable requests={requests} />
    </div>
  );
}