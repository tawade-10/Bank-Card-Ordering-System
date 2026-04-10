import React, { useEffect, useState } from "react";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";
import "./Dashboard.css";
import MyCards from "../MyCards/MyCards";
import RecentCardTable from "../RecentCardTable/RecentCardTable";

export default function Dashboard() {
  const [requests, setRequests] = useState([]);
  const navigate = useNavigate();
  const [role, setRole] = useState(localStorage.getItem("role"));

  useEffect(() => {
    if (role === "CUSTOMER" || role === "ADMIN") {
      loadRequests();
    }
  }, []);

  const loadRequests = async () => {
    try {
      const token = localStorage.getItem("token");

      const response = await axios.get("http://localhost:8080/api/request-card", {
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

  const updateStatus = async (id, newStatus) => {
    try {
      const token = localStorage.getItem("token");

      await axios.put(
        `http://localhost:8080/api/request-card/${id}`,
        { status: newStatus },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      alert(`Status updated to ${newStatus}`);
      loadRequests();
    } catch (error) {
      console.error("Status update failed:", error);
      alert("Failed to update status.");
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
            <Link to="/admin/dashboard/manage-customers" className="box">Pending</Link>
            <Link to="/admin/dashboard/manage-requests" className="box">Approved</Link>
            <Link to="/admin/dashboard/reports" className="box">Rejected</Link>
          </>
        ) : (
          <>
            <Link to="/dashboard/request-new-card" className="box">Request New Card</Link>
            <Link to="/dashboard/my-cards" className="box">My Cards</Link>
            <Link to="/dashboard/track-requests" className="box">Track Requests</Link>
          </>
        )}
      </div>

      <hr className="divider" />

      {role === "ADMIN" ? (
        <div>
          <h3 className="section-title">Admin Operations</h3>
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
                      <td>{req.customerName || "N/A"}</td>
                      <td>{req.cardType}</td>
                      <td>{req.cardVariant}</td>
                      <td>{req.reason}</td>
                      <td>
                        {req.status}
                      </td>
                      <td>{req.localDate}</td>
                      <td>
{/*                         <button */}
{/*                           onClick={() => updateStatus(req.requestId, "APPROVED")} */}
{/*                           className="approve-btn" */}
{/*                         > */}
{/*                           Approve */}
{/*                         </button> */}
                        <button
                          onClick={() => updateStatus(req.requestId, "REJECTED")}
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