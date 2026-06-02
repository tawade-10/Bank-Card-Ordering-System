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
    { key: "DELIVERED", label: "Delivered" },
  ];

  const formatDate = (date) => {
    if (!date) return "--";
    const [y, m, d] = date.split("-");
    return `${d}/${m}/${y}`;
  };

  const formatDateTime = (date, time) => {
    if (!date || !time) return "--";
    return `${formatDate(date)} ${time}`;
  };

  const getTimelineTime = () => {
    if (request.updatedDate && request.updatedTime)
      return formatDateTime(request.updatedDate, request.updatedTime);

    if (request.createdDate && request.createdTime)
      return formatDateTime(request.createdDate, request.createdTime);

    return "--";
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

  const currentIndex = steps.findIndex((s) => s.key === request.requestStatus);

  return (
    <div className="view-modal-overlay">
      <div className="view-modal-container">
        <button className="close-btn" onClick={closeModal}>
          <IoClose size={28} />
        </button>

        <div className="header-box">
          <h2 className="view-title">Request Timeline</h2>
          <h3 className="sub-heading">Request ID : {request.requestId}</h3>
          <h3 className="sub-heading">Request Status : {request.requestStatus}</h3>
        </div>

        <div className="timeline-container">
          {steps.map((step, index) => (
            <div key={step.key} className="timeline-wrapper">
              <div className="timeline-item">
                <div
                  className={`dot ${index <= currentIndex ? "active" : ""}`}
                ></div>

                <div className="content">
                  <h3>{step.label}</h3>

                  {index === currentIndex && (
                    <p className="date-text">{getTimelineTime()}</p>
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