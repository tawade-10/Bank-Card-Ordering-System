import React, { useEffect, useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import "./Dashboard.css";

export default function Dashboard() {
  const role = localStorage.getItem("role");
  const [requests, setRequests] = useState([]);

  useEffect(() => {
    if (role === "CUSTOMER") {
      loadMyRequests();
    }
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
      alert("Failed to fetch card requests. Please check the console.");
    }
  };

  return (
    <div className="dashboard-wrapper">
      {role === "ADMIN" ? (
        <>
          <h2>Admin Dashboard</h2>
          <div className="top-boxes">
            <Link to="/dashboard/all-users" className="box">
              View All Users
            </Link>
            <Link to="/dashboard/all-requests" className="box">
              All Card Requests
            </Link>
            <Link to="/dashboard/reports" className="box">
              Reports
            </Link>
          </div>
        </>
      ) : (
        <>
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
          <h3 className="section-title">Recent Card Requests</h3>

          <div className="table-container">
            <table className="custom-table">
              <thead>
                <tr>
                  <th>Request ID</th>
                  <th>Card Type</th>
                  <th>Status</th>
                  <th>Date</th>
                </tr>
              </thead>

              <tbody>
                {requests.length > 0 ? (
                  requests.map((req) => (
                    <tr key={req.requestId}>
                      <td>{req.requestId}</td>
                      <td>{req.cardType}</td>
                      <td>{req.statusOfRequest}</td>
                      <td>{req.localDate}</td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="4" style={{ textAlign: "center" }}>
                      No requests found.
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
        </>
      )}
    </div>
  );
}