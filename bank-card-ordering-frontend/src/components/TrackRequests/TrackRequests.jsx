import React, { useEffect, useState } from "react";
import axios from "axios";
import "./TrackRequests.css";
import Tabs from "@mui/material/Tabs";
import Tab from "@mui/material/Tab";
import Box from "@mui/material/Box";
import { useNavigate } from "react-router-dom";

export default function TrackRequests() {
  const [requests, setRequests] = useState([]);
  const [tabValue, setTabValue] = useState(-1);

  const navigate = useNavigate();
  const token = localStorage.getItem("token");

  useEffect(() => {
    loadRequests();
  }, []);

  const loadRequests = async () => {
    try {
      const res = await axios.get(
        "http://localhost:8080/api/request-card/email",
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      setRequests(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  const handleTabChange = (event, newValue) => {
    setTabValue(newValue);
  };

  const filteredRequests = requests.filter((req) => {
    if (tabValue === 1) return req.status === "PENDING_REVIEW";
    if (tabValue === 2) return req.status === "APPROVED";
    if (tabValue === 3) return req.status === "REJECTED";
    return true;
  });

  const getProgress = (status) => {
    const steps = ["PENDING_REVIEW", "APPROVED", "PRINTING", "DISPATCHED", "DELIVERED"];
    const currentIndex = steps.indexOf(status);

    return (
      <div className="progress-bar">
        {steps.map((step, index) => (
          <span
            key={index}
            className={index <= currentIndex ? "step active" : "step"}
          ></span>
        ))}
      </div>
    );
  };

  return (
    <div className="track-container">
      <Box sx={{ width: "100%" }}>
        <Tabs
          value={tabValue}
          onChange={handleTabChange}
          centered
          indicatorColor="primary"
          textColor="primary"
        >
          <Tab label="All" />
          <Tab label="Pending" />
          <Tab label="Approved" />
          <Tab label="Rejected" />
        </Tabs>
      </Box>

      <h2 className="track-title">Track Your Card Requests</h2>

      <div className="table-wrapper">
        <table className="track-table">
          <thead>
            <tr>
              <th>Request ID</th>
              <th>Card Type</th>
              <th>Variant</th>
              <th>Reason</th>
              <th>Status</th>
              <th>Date</th>
{/*               <th>Progress</th> */}
              <th>Actions</th>
            </tr>
          </thead>

          <tbody>
            {filteredRequests.length > 0 ? (
              filteredRequests.map((req) => (
                <tr key={req.requestId}>
                  <td>{req.requestId}</td>
                  <td>{req.cardType}</td>
                  <td>{req.cardVariant}</td>
                  <td>{req.reason}</td>
                  <td>
                    <span className={`status ${req.status.toLowerCase()}`}>
                      {req.status}
                    </span>
                  </td>
                  <td>{req.localDate}</td>
{/*                   <td>{getProgress(req.status)}</td> */}
                  <td>
                    <button
                      className="view-btn"
                      onClick={() =>
                        navigate(`/dashboard/view-request/${req.requestId}`)
                      }
                    >
                      View
                    </button>
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="8" className="empty-row">
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