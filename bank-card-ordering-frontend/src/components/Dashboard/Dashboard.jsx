import React, { useEffect, useState } from "react";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";
import "./Dashboard.css";
import MyCards from "../MyCards/MyCards";
import RecentCardTable from "../RecentCardTable/RecentCardTable";

export default function Dashboard() {
  const [requests, setRequests] = useState([]);
  const navigate = useNavigate();
  const role = localStorage.getItem("role");

  useEffect(() => {
    loadRequests();
  }, []);

  // Fetch requests based on role
  const loadRequests = async () => {
    try {
      const token = localStorage.getItem("token");

      let url =
        role === "ADMIN"
          ? "http://localhost:8080/api/request-card"
          : "http://localhost:8080/api/request-card/email";

      const response = await axios.get(url, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      setRequests(response.data);
    } catch (error) {
      console.error("Error fetching requests:", error);
      alert("Failed to fetch card requests.");
    }
  };

  return (
    <div className="dashboard-wrapper">
      <h2 className="dashboard-title">
        {role === "ADMIN" ? "Admin Dashboard" : "Dashboard"}
      </h2>

      <div className="top-boxes">
        {role === "ADMIN" ? (
          <>
            <Link to="/admin/dashboard/pending" className="box">Pending</Link>
            <Link to="/admin/dashboard/approved" className="box">Approved</Link>
            <Link to="/admin/dashboard/rejected" className="box">Rejected</Link>
          </>
        ) : (
          <>
            <Link to="/dashboard/request-new-card" className="box">
              Request New Card
            </Link>
            <Link to="/dashboard/my-cards" className="box">
              My Cards
            </Link>
            <Link to="/dashboard/track-requests" className="box">
              Track Requests
            </Link>
          </>
        )}
      </div>

      <hr className="divider" />

      {role === "ADMIN" ? (
        <div>
          <h3 className="section-title">Manage Requests</h3>

          <div className="table-container">
            <table className="admin-table">
              <thead>
                <tr>
                  <th>Request ID</th>
                  <th>Customer Name</th>
                  <th>Card Type</th>
                  <th>Variant</th>
                  <th>Reason</th>
                  <th>Status</th>
                  <th>Date</th>
                  <th>Action</th>
                </tr>
              </thead>

              <tbody>
                {requests?.length > 0 ? (
                  requests.map((req) => (
                    <tr key={req.requestId}>
                      <td>{req.requestId}</td>
                      <td>{req.customers?.customerName || "N/A"}</td>
                      <td>{req.cardType}</td>
                      <td>{req.cardVariant}</td>
                      <td>{req.reason}</td>
                      <td>{req.status}</td>
                      <td>{req.localDate}</td>
                      <td>
                        <button
                          onClick={() => {
                            if (req.status === "APPROVED") {
                              navigate(`/admin/dashboard/create-card/${req.requestId}`);
                            } else {
                              navigate(`/admin/dashboard/view-request/${req.requestId}`);
                            }
                          }}
                          className="reject-btn"
                        >
                          View
                        </button>
                      </td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="8" style={{ textAlign: "center" }}>
                      No Request Found
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
        </div>
      ) : (
        <>
          {/* CUSTOMER VIEW */}
          <h3 className="section-title">My Active Cards</h3>
          <MyCards />

          <hr className="divider" />

          <h3 className="section-title">Recent Card Requests</h3>
          <RecentCardTable requests={requests} />
        </>
      )}
    </div>
  );
}