import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom";
import ReasonBox from "../ReasonBox/ReasonBox";
import "./ViewRequestsAdmin.css";

export default function ViewRequests() {
  const { requestId } = useParams();
  const navigate = useNavigate();

  const [request, setRequest] = useState(null);
  const [showReasonBox, setShowReasonBox] = useState(false);
  const [actionType, setActionType] = useState("");

  const token = localStorage.getItem("token");

  useEffect(() => {
    loadRequest();
  }, []);

  /** Fetch request details */
  const loadRequest = async () => {
    try {
      const res = await axios.get(
        `http://localhost:8080/api/request-card/${requestId}`,
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setRequest(res.data);
    } catch (err) {
      console.error(err);
      alert("Failed to load request details");
    }
  };

  const handleReview = async (newStatus, reason) => {
    try {
      const res = await axios.put(
        `http://localhost:8080/api/request-card/${requestId}/review`,
        {
          status: newStatus,
          reviewMessage: reason,
        },
        { headers: { Authorization: `Bearer ${token}` } }
      );

      setRequest(res.data);
      navigate("/admin/dashboard");
    } catch (err) {
      alert(err.response?.data || "Failed to update");
    }
  };

  const handleFlowUpdate = async (newStatus) => {
    try {
      const res = await axios.put(
        `http://localhost:8080/api/request-card/${requestId}/status`,
        { status: newStatus },
        { headers: { Authorization: `Bearer ${token}` } }
      );

      setRequest(res.data);
      navigate("/admin/dashboard");
    } catch (err) {
      alert(err.response?.data || "Failed to update status");
    }
  };

  if (!request) return <h3>Loading...</h3>;

return (
  <div className="vr-wrapper">
    <div className={`vr-overlay ${showReasonBox ? "show" : ""}`} />

    <div className="vr-card">
      <h2 className="vr-title">Request Details</h2>

      <div className="vr-details">
        <div className="vr-row"><b>Request ID:</b> {request.requestId}</div>
        <div className="vr-row"><b>Customer Name:</b> {request.customerName}</div>
        <div className="vr-row"><b>Card Type:</b> {request.cardType}</div>
        <div className="vr-row"><b>Variant:</b> {request.cardVariant}</div>
        <div className="vr-row"><b>Reason:</b> {request.reason}</div>
        <div className="vr-row"><b>Status:</b> <span className={`status-badge ${request.status.toLowerCase()}`}>{request.status}</span></div>
        <div className="vr-row"><b>Date:</b> {request.localDate}</div>
        <div className="vr-row"><b>Review Message:</b> {request.reviewMessage || "None"}</div>
      </div>

      {request.status === "PENDING_REVIEW" && (
        !showReasonBox ? (
          <div className="vr-btn-group">
            <button
              className="btn-approve"
              onClick={() => {
                setActionType("APPROVED");
                setShowReasonBox(true);
              }}
            >
              Approve
            </button>

            <button
              className="btn-reject"
              onClick={() => {
                setActionType("REJECTED");
                setShowReasonBox(true);
              }}
            >
              Reject
            </button>
          </div>
        ) : (
          <ReasonBox
            actionType={actionType}
            onSubmit={(reason) => handleReview(actionType, reason)}
            onCancel={() => setShowReasonBox(false)}
          />
        )
      )}

      {request.status === "APPROVED" && !showReasonBox && (
        <div className="vr-action">
          <button
            className="btn-create"
            onClick={() => navigate(`/admin/dashboard/create-card/${request.requestId}`)}
          >
            Create Card
          </button>
        </div>
      )}

      {["PRINTED", "DISPATCHED"].includes(request.status) && (
        <div className="vr-status-box">
          <label><b>Update Status : </b></label>
          <select
            className="vr-select"
            value=""
            onChange={(e) => e.target.value && handleFlowUpdate(e.target.value)}
          >
            <option value="">-- Select Next Status --</option>

{/*             {request.status === "APPROVED" && <option value="PRINTED">PRINTED</option>} */}
            {request.status === "PRINTED" && <option value="DISPATCHED">DISPATCHED</option>}
            {request.status === "DISPATCHED" && <option value="DELIVERED">DELIVERED</option>}
          </select>
        </div>
      )}

      {request.status === "DELIVERED" && (
        <div className="vr-action">
          <button className="btn-back" onClick={() => navigate("/admin/dashboard")}>
            Back to Dashboard
          </button>
        </div>
      )}
    </div>
  </div>
);
}

