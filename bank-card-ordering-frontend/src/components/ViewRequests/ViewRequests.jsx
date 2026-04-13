import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom";
import "./ViewRequests.css";

export default function ViewRequests() {
  const { requestId } = useParams();
  const navigate = useNavigate();
  const [request, setRequest] = useState(null);

  useEffect(() => {
    loadRequest();
  }, []);

  const loadRequest = async () => {
    try {
      const token = localStorage.getItem("token");

      const response = await axios.get(
        `http://localhost:8080/api/request-card/${requestId}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      setRequest(response.data);
    } catch (error) {
      console.error("Error fetching request:", error);
      alert("Failed to load request details");
    }
  };

    const updateRequest = async (newStatus) => {
      try {
        const token = localStorage.getItem("token");

        const response = await axios.put(
          `http://localhost:8080/api/request-card/${requestId}`,
          { status: newStatus },
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
        alert(`Status updated to ${newStatus}`);
        setRequest({ ...request, status: newStatus });
        navigate("/admin/dashboard");

      } catch (error) {
        console.error("Update failed:", error);
        alert("Failed to update request");
      }
    };

  if (!request) return <h3>Loading...</h3>;

  return (
    <div className="view-req-container">
      <h2 className="view-req-title">Request Details</h2>

      <p className="view-req-field"><b>Request ID:</b> {request.requestId}</p>
      <p className="view-req-field"><b>Customer Name:</b> {request.customerName}</p>
      <p className="view-req-field"><b>Card Type:</b> {request.cardType}</p>
      <p className="view-req-field"><b>Variant:</b> {request.cardVariant}</p>
      <p className="view-req-field"><b>Reason:</b> {request.reason}</p>
      <p className="view-req-field">
        <b>Status:</b>{" "}
        <span>{request.status}</span>
      </p>

      <p className="view-req-field"><b>Date:</b> {request.localDate}</p>

      <div className="button-group">
        <button
          className="btn-approve"
          onClick={() => updateRequest("APPROVED")}
        >
          Approve
        </button>

        <button
          className="btn-reject"
          onClick={() => updateRequest("REJECTED")}
        >
          Reject
        </button>
      </div>
    </div>
  );
}