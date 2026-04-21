import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom";
import ReasonBox from "../ReasonBox/ReasonBox";
import "./ViewRequests.css";

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

  /** For APPROVE / REJECT */
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

  /** FLOW UPDATE → PRINTING → DISPATCHED → DELIVERED */
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
    <div className="view-req-wrapper">
      <div className={`overlay ${showReasonBox ? "show" : ""}`} />

      <div className="view-req-container">
        <h2 className="view-req-title">Request Details</h2>
        <p><b>Request ID:</b> {request.requestId}</p>
        <p><b>Customer Name:</b> {request.customers?.customerName}</p>
        <p><b>Card Type:</b> {request.cardType}</p>
        <p><b>Variant:</b> {request.cardVariant}</p>
        <p><b>Reason:</b> {request.reason}</p>
        <p><b>Status:</b> {request.status}</p>
        <p><b>Date:</b> {request.localDate}</p>
        <p><b>Review Message:</b> {request.reviewMessage || "None"}</p>

        {request.status === "PENDING_REVIEW" && (
          <>
            {!showReasonBox ? (
              <div className="button-group">
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
            )}
          </>
        )}

        {request.status === "APPROVED" && !showReasonBox && (
          <div className="create-card-box">
            <button
              className="btn-create-card"
              onClick={() => navigate(`/admin/dashboard/create-card/${request.requestId}`)}
            >
              Create Card
            </button>
          </div>
        )}

        {["APPROVED", "PRINTING", "DISPATCHED"].includes(request.status) && (
          <div className="status-dropdown-box">
            <label><b>Update Status:</b></label>

            <select
              className="flow-dropdown"
              value=""
              onChange={(e) => {
                const next = e.target.value;
                if (next !== "") handleFlowUpdate(next);
              }}
            >
              <option value="">-- Select Next Status --</option>

              {request.status === "APPROVED" && (
                <option value="PRINTING">PRINTING</option>
              )}

              {request.status === "PRINTING" && (
                <option value="DISPATCHED">DISPATCHED</option>
              )}

              {request.status === "DISPATCHED" && (
                <option value="DELIVERED">DELIVERED</option>
              )}
            </select>
          </div>
        )}

        {request.status === "DELIVERED" && (
          <button
            className="btn-back"
            onClick={() => navigate("/admin/dashboard")}
          >
            Back to Dashboard
          </button>
        )}
      </div>
    </div>
  );
}