import React, { useEffect, useState } from "react";
import axios from "axios";
import "./TrackRequests.css";
import Tabs from "@mui/material/Tabs";
import Tab from "@mui/material/Tab";
import Box from "@mui/material/Box";
import ViewRequestsCustomer from "../ViewRequestsCustomer/ViewRequestsCustomer";

export default function TrackRequests() {
  const [requests, setRequests] = useState([]);
  const [tabValue, setTabValue] = useState(-1);

  const [openModal, setOpenModal] = useState(false);
  const [selectedId, setSelectedId] = useState(null);

  const token = localStorage.getItem("token");

  useEffect(() => {
    loadRequests();
  }, []);

  const loadRequests = async () => {
    try {
      const res = await axios.get("http://localhost:8080/api/request-card/email", {
        headers: { Authorization: `Bearer ${token}` },
      });
      setRequests(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  const handleTabChange = (event, newValue) => setTabValue(newValue);

  const filteredRequests = requests.filter((req) => {
    if (tabValue === 1) return req.status === "PENDING_REVIEW";
    if (tabValue === 2) return req.status === "APPROVED";
    if (tabValue === 3) return req.status === "REJECTED";
    if (tabValue === 4) return req.status === "PRINTED";
    if (tabValue === 5) return req.status === "DISPATCHED";
    if (tabValue === 6) return req.status === "DELIVERED";
    return true;
  });

  const openViewModal = (id) => {
    setSelectedId(id);
    setOpenModal(true);
  };

  const closeModal = () => {
    setOpenModal(false);
    setSelectedId(null);
  };

  return (
    <div className="track-container">
      <Box sx={{ width: "100%" }}>
        <Tabs
          value={tabValue}
          onChange={handleTabChange}
          centered
          indicatorColor="primary"
          textColor="tertiary"
        >
          <Tab label="All" />
          <Tab label="Pending" />
          <Tab label="Approved" />
          <Tab label="Rejected" />
          <Tab label="Printed" />
          <Tab label="Dispatched" />
          <Tab label="Delivered" />
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
                    <span className={`status ${req.status.toLowerCase()}`}>{req.status}</span>
                  </td>
                  <td>{req.localDate}</td>

                  <td>
                    <button
                      className="view-btn"
                      onClick={() => openViewModal(req.requestId)}
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
      {openModal && (
        <ViewRequestsCustomer requestId={selectedId} closeModal={closeModal} />
      )}
    </div>
  );
}