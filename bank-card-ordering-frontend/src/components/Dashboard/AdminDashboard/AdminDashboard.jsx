import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "./AdminDashboard.css";

export default function AdminDashboard() {
  const [requests, setRequests] = useState([]);
  const [selectedStatus,setSelectedStatus] = useState("ALL");
  const navigate = useNavigate();
  const token = localStorage.getItem("token");

  useEffect(() => {
    loadRequests();
  }, []);

  const loadRequests = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/request-card", {
        headers: { Authorization: `Bearer ${token}` },
      });
      setRequests(response.data);
    } catch (error) {
      console.error("Error loading requests:", error);
    }
  };

  const total = requests.length;
  const pending = requests.filter((r) => r.status === "PENDING_REVIEW").length;
  const approved = requests.filter((r) => r.status === "APPROVED").length;
  const rejected = requests.filter((r) => r.status === "REJECTED").length;
  const dispatched = requests.filter((r) => r.status === "DISPATCHED").length;
  const delivered = requests.filter((r) => r.status === "DELIVERED").length;

  const filteredRequests =
    selectedStatus === "ALL"
      ? requests
      : requests.filter(r => r.status === selectedStatus);

  return (
    <div className="admin-dashboard-wrapper">
      <h2 className="title">Admin Dashboard</h2>

      <div className="six-box-container">

        <div className="box" onClick={() => setSelectedStatus("PENDING_REVIEW")}>
          <h3>{pending}</h3><p>Pending</p>
        </div>

        <div className="box" onClick={() => setSelectedStatus("APPROVED")}>
          <h3>{approved}</h3><p>Approved</p>
        </div>

        <div className="box" onClick={() => setSelectedStatus("REJECTED")}>
          <h3>{rejected}</h3><p>Rejected</p>
        </div>

        <div className="box" onClick={() => setSelectedStatus("DISPATCHED")}>
          <h3>{dispatched}</h3><p>Dispatched</p>
        </div>

        <div className="box" onClick={() => setSelectedStatus("DELIVERED")}>
          <h3>{delivered}</h3><p>Delivered</p>
        </div>

        <div className="box" onClick={() => setSelectedStatus("ALL")}>
          <h3>{total}</h3><p>Total Requests</p>
        </div>
      </div>

      {/* TABLE */}
      <div className="table-box">
        <table className="admin-table">
          <thead>
            <tr>
              <th>Request ID</th>
              <th>Customer</th>
              <th>Card Type</th>
              <th>Variant</th>
              <th>Reason</th>
              <th>Status</th>
              <th>Date</th>
              <th>Action</th>
            </tr>
          </thead>

          <tbody>
            {requests.length > 0 ? (
              filteredRequests.map((req) => (
                <tr key={req.requestId}>
                  <td>{req.requestId}</td>
                  <td>{req.customerName}</td>
                  <td>{req.cardType}</td>
                  <td>{req.cardVariant}</td>
                  <td>{req.reason}</td>
                  <td className={`status ${req.status.toLowerCase()}`}>
                    {req.status}
                  </td>
                  <td>{req.localDate}</td>
                  <td>
                    <button
                      className="view-update-btn"
                      onClick={() =>
                        navigate(`/admin/dashboard/view-request/${req.requestId}`)
                      }
                    >
                      View
                    </button>
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="8" style={{ textAlign: "center" }}>
                  No Requests Found
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}