import React, { useEffect, useState } from "react";
import axios from "axios";
import AdminLayout from "../Sidebar/AdminLayout/AdminLayout";
import { useNavigate } from "react-router-dom";
import "./AccountsTable.css";

export default function AccountsTable() {

  const [requests, setRequests] = useState([]);
  const [selectedStatus, setSelectedStatus] = useState("ALL");

  const navigate = useNavigate();
  const token = localStorage.getItem("token");

  useEffect(() => {
    loadRequests();
  }, []);

  const loadRequests = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8080/api/account-requests",
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      setRequests(response.data);

    } catch (error) {
      console.error("Error loading account requests", error);
    }
  };

  const total = requests.length;

  const pending = requests.filter(
    (r) => r.requestStatus === "PENDING_REVIEW"
  ).length;

  const approved = requests.filter(
    (r) => r.requestStatus === "APPROVED"
  ).length;

  const rejected = requests.filter(
    (r) => r.requestStatus === "REJECTED"
  ).length;

  const created = requests.filter(
    (r) => r.requestStatus === "ACTIVE"
  ).length;

  const filteredRequests =
    selectedStatus === "ALL"
      ? requests
      : requests.filter(
          (r) => r.requestStatus === selectedStatus
        );

  return (
    <AdminLayout>
      <div className="admin-dashboard-wrapper">
        <h2 className="page-title">
          Bank Account Requests
        </h2>

        <div className="six-box-container">
          <div
            className="box"
            onClick={() =>
              setSelectedStatus("PENDING_REVIEW")
            }
          >
            <h3>{pending}</h3>
            <p>Pending</p>
          </div>
          <div
            className="box"
            onClick={() =>
              setSelectedStatus("APPROVED")
            }
          >
            <h3>{approved}</h3>
            <p>Approved</p>
          </div>
          <div
            className="box"
            onClick={() =>
              setSelectedStatus("REJECTED")
            }
          >
            <h3>{rejected}</h3>
            <p>Rejected</p>
          </div>
          <div
            className="box"
            onClick={() =>
              setSelectedStatus("ACCOUNT_CREATED")
            }
          >
            <h3>{created}</h3>
            <p>Created</p>
          </div>
          <div
            className="box"
            onClick={() =>
              setSelectedStatus("ALL")
            }
          >
            <h3>{total}</h3>
            <p>Total</p>
          </div>
        </div>

        <div className="table-box">
          <table className="admin-table">
            <thead>
              <tr>
                <th>Request ID</th>
                <th>Customer</th>
                <th>Bank</th>
                <th>Branch</th>
                <th>Account Type</th>
                <th>Status</th>
                <th>Date</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {filteredRequests.length > 0 ? (
                filteredRequests.map((request) => (
                  <tr key={request.requestId}>
                    <td>{request.requestId}</td>
                    <td>{request.customerName}</td>
                    <td>{request.bankName}</td>
                    <td>{request.branchName}</td>
                    <td>{request.accountType}</td>
                    <td
                      className={`status ${request.requestStatus.toLowerCase()}`}
                    >
                      {request.requestStatus}
                    </td>
                    <td>{request.requestDate}</td>
                    <td>
                      <button
                        className="view-update-btn"
                        onClick={() =>
                          navigate(`/admin/dashboard/view-account-request/${request.requestId}`)
                        }
                      >
                        View
                      </button>
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td
                    colSpan="8"
                    style={{ textAlign: "center" }}
                  >
                    No Requests Found
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </AdminLayout>
  );
}