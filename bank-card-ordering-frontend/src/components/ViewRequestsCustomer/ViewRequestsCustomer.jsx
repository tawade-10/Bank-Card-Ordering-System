import React, { useEffect, useState } from "react";
import axios from "axios";
import "./ViewRequestsCustomer.css";
import { IoClose } from "react-icons/io5";

export default function ViewRequestsCustomer({ requestId, closeModal }) {
  const [request, setRequest] = useState(null);
  const token = localStorage.getItem("token");

  const steps = [
    { key: "PENDING_REVIEW", label: "Pending Review" },
    { key: "APPROVED", label: "Approved" },
    { key: "PRINTED", label: "Printed" },
    { key: "DISPATCHED", label: "Dispatched" },
    { key: "DELIVERED", label: "Delivered" }
  ];

  const formatDateTime = (dateTime) => {
    const d = new Date(dateTime);
    const year = d.getFullYear();
    const month = String(d.getMonth() + 1).padStart(2, "0");
    const day = String(d.getDate()).padStart(2, "0");
    const hh = String(d.getHours()).padStart(2, "0");
    const mm = String(d.getMinutes()).padStart(2, "0");

    return `${year}-${month}-${day} ${hh}:${mm}`;
  };

  useEffect(() => {
    loadRequestDetails();
  }, []);

  const loadRequestDetails = async () => {
    try {
      const res = await axios.get(
        `http://localhost:8080/api/request-card/${requestId}`,
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setRequest(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  if (!request) return null;

  const currentIndex = steps.findIndex(s => s.key === request.status);

  return (
    <div className="view-modal-overlay">
      <div className="view-modal-container">
        <button className="close-btn" onClick={closeModal}>
          <IoClose size={28} />
        </button>

        <div className="header-box">
          <h2 className="view-title">Request Timeline</h2>
          <h3 className="sub-heading">Request ID : {request.requestId}</h3>
          <h3 className="sub-heading">Request Status : {request.status}</h3>
        </div>
        <div className="timeline-container">
          {steps.map((step, index) => (
            <div key={step.key} className="timeline-wrapper">
              <div className="timeline-item">
                <div
                  className={`dot ${
                    index <= currentIndex ? "active" : ""
                  }`}
                ></div>

                <div className="content">
                  <h3>{step.label}</h3>
                  {index === currentIndex && (
                    <p className="date-text">{formatDateTime(request.updatedAt)}</p>
                  )}
                </div>
              </div>

              {index < steps.length - 1 && (
                <div
                  className={`timeline-line ${
                    index < currentIndex ? "active-line" : ""
                  }`}
                ></div>
              )}
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}