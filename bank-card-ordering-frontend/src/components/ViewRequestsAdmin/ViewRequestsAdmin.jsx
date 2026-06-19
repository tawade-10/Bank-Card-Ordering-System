import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom";
import ReasonBox from "../ReasonBox/ReasonBox";
import "./ViewRequestsAdmin.css";
import { toast } from "react-toastify";

export default function ViewRequestsAdmin() {
  const { requestId } = useParams();
  const navigate = useNavigate();

  const [request, setRequest] = useState(null);
  const [showReasonBox, setShowReasonBox] = useState(false);
  const [actionType, setActionType] = useState("");

  const token = localStorage.getItem("token");

  useEffect(() => {
    loadRequest();
  }, []);

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

  const fetchLatestNotification = async (customerId) => {
    try {
      const res = await axios.get(
        `http://localhost:8080/api/notifications/latest/${customerId}`,
        { headers: { Authorization: `Bearer ${token}` } }
      );

      if (res.data?.message) {
        toast.info(res.data.message);
      }
    } catch (err) {
      console.log("Notification fetch failed", err);
    }
  };

  const handleReview = async (newStatus, reason) => {
    try {
      const res = await axios.put(
        `http://localhost:8080/api/request-card/${requestId}/review`,
        {
          requestStatus: newStatus,
          reviewMessage: reason,
        },
        { headers: { Authorization: `Bearer ${token}` } }
      );

      setRequest(res.data);

      if (res.data.customerId) {
        fetchLatestNotification(res.data.customerId);
      }

      navigate("/admin/dashboard");
    } catch (err) {
      alert(err.response?.data || "Failed to update");
    }
  };

  const handleFlowUpdate = async (newStatus) => {
    try {
      const res = await axios.put(
        `http://localhost:8080/api/request-card/${requestId}/requestStatus`,
        { requestStatus: newStatus },
        { headers: { Authorization: `Bearer ${token}` } }
      );

      setRequest(res.data);

      if (res.data.customerId) {
        fetchLatestNotification(res.data.customerId);
      }

      navigate("/admin/dashboard");
    } catch (err) {
      alert(err.response?.data || "Failed to update requestStatus");
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
          <div className="vr-row"><b>Status:</b> <span className={`requestStatus-badge ${request.requestStatus.toLowerCase()}`}>{request.requestStatus}</span></div>
          <div className="vr-row"><b>Date:</b> {request.createdDate}</div>
          <div className="vr-row"><b>Review Message:</b> {request.reviewMessage || "None"}</div>
        </div>

        {request.requestStatus === "PENDING_REVIEW" && (
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

{/*         {request.requestStatus === "APPROVED" && !showReasonBox && ( */}
{/*           <div className="vr-action"> */}
{/*             <button */}
{/*               className="btn-create" */}
{/*               onClick={() => navigate(`/admin/dashboard/create-card/${request.requestId}`)} */}
{/*             > */}
{/*               Create Card */}
{/*             </button> */}
{/*           </div> */}
{/*         )} */}

        {request.requestStatus === "APPROVED" && !showReasonBox && (
          <div className="vr-action">
            <button
              className="btn-create"
              onClick={() => {
                console.log("REQUEST OBJECT 👉", request);

                const id = request?.requestId || request?.id || request?.cardRequestId;

                console.log("USING ID 👉", id);

                if (!id) {
                  alert("Request ID missing! Check backend response.");
                  return;
                }

                navigate(`/admin/dashboard/create-card/${id}`);
              }}
            >
              Create Card
            </button>
          </div>
        )}

        {["PRINTED", "DISPATCHED"].includes(request.requestStatus) && (
          <div className="vr-requestStatus-box">
            <label><b>Update Status : </b></label>
            <select
              className="vr-select"
              value=""
              onChange={(e) => e.target.value && handleFlowUpdate(e.target.value)}
            >
              <option value="">-- Select Next Status --</option>
              {request.requestStatus === "PRINTED" && <option value="DISPATCHED">DISPATCHED</option>}
              {request.requestStatus === "DISPATCHED" && <option value="DELIVERED">DELIVERED</option>}
            </select>
          </div>
        )}

        {request.requestStatus === "DELIVERED" && (
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

