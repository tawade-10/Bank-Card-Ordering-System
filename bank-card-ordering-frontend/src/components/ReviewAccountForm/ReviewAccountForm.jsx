import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom";
import "./ReviewAccountForm.css";

export default function ReviewAccountForm() {
  const { accountRequestId } = useParams();
  const navigate = useNavigate();

  const [request, setRequest] = useState(null);

  const token = localStorage.getItem("token");

  useEffect(() => {
    loadRequest();
  }, []);

  const loadRequest = async () => {
    try {
      const res = await axios.get(
        `http://localhost:8080/api/account/request/${accountRequestId}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      setRequest(res.data);
    } catch (err) {
      console.error(err);
      alert("Failed to load account request details");
    }
  };

  const updateStatus = async (status) => {
    try {
      const res = await axios.put(
        `http://localhost:8080/api/account/update/${accountRequestId}?accountStatus=${status}`,
        null,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      setRequest(res.data);

      alert(status === "APPROVED" ? "Account created successfully!"
                                  : "Request rejected successfully!"
      );

      loadRequest();

    } catch (err) {
      console.error(err);
      alert(err.response?.data?.message || "Something went wrong");
    }
  };

  if (!request) {
    return <h3>Loading...</h3>;
  }

  return (
    <div className="vr-wrapper">
      <div className="vr-card">
        <h2 className="vr-title">Account Request Details</h2>

        <div className="vr-details">
          <div className="vr-row">
            <b>Request ID:</b> {request.accountRequestId}
          </div>

          <div className="vr-row">
            <b>Customer ID:</b> {request.customerId}
          </div>

          <div className="vr-row">
            <b>Customer Name:</b> {request.customerName}
          </div>

          <div className="vr-row">
            <b>Bank:</b> {request.bankName}
          </div>

          <div className="vr-row">
            <b>Branch:</b> {request.branchName}
          </div>

          <div className="vr-row">
            <b>Account Type:</b> {request.accountType}
          </div>

          <div className="vr-row">
            <b>Status:</b>
            <span
              className={`requestStatus-badge ${request.accountStatus
                ?.toLowerCase()
                .replace("_", "-")}`}
              style={{ marginLeft: "10px" }}
            >
              {request.accountStatus}
            </span>
          </div>

          <div className="vr-row">
            <b>Created At:</b>{" "}
            {request.createdDate
              ? new Date(request.createdDate).toLocaleString()
              : "-"}
          </div>

          <div className="vr-row">
            <b>Updated At:</b>{" "}
            {request.updatedDate
              ? new Date(request.updatedDate).toLocaleString()
              : "-"}
          </div>

          <div className="vr-row">
            <b>Message:</b> {request.message || "N/A"}
          </div>
        </div>

        {request.accountStatus === "PENDING_REVIEW" && (
          <div className="vr-btn-group">
            <button
              className="btn-approve"
              onClick={() => updateStatus("APPROVED")}
            >
              Approve
            </button>

            <button
              className="btn-reject"
              onClick={() => updateStatus("REJECTED")}
            >
              Reject
            </button>
          </div>
        )}

        <div className="vr-action">
          <button
            className="btn-back"
            onClick={() => navigate("/admin/account-requests")}
          >
            Back
          </button>
        </div>
      </div>
    </div>
  );
}