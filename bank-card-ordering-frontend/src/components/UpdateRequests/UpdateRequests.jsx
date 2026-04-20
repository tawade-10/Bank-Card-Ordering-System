import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom";
import "./UpdateRequests.css";

export default function UpdateRequests() {
  const { requestId } = useParams();
  const navigate = useNavigate();
  const [request, setRequest] = useState(null);
  const [status, setStatus] = useState("");
  const token = localStorage.getItem("token");

  useEffect(() => { loadRequest(); }, []);

  const loadRequest = async () => {
    try {
      const res = await axios.get(
        `http://localhost:8080/api/request-card/${requestId}`,
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setRequest(res.data);
    } catch (err) {
      alert("Error fetching request");
    }
  };

  const getNextStatus = () => {
    if (!request) return [];

    switch (request.status) {
      case "PENDING_REVIEW":
        return ["APPROVED", "REJECTED"];

      case "APPROVED":
        return ["PRINTING","DISPATCHED","DELIVERED"];

      case "PRINTING":
        return ["DISPATCHED","DELIVERED"];

      case "DISPATCHED":
        return ["DELIVERED"];

      default:
        return [];
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!status) {
      alert("Select a status");
      return;
    }

    try {
      if (request.status === "PENDING_REVIEW") {
        await axios.put(
          `http://localhost:8080/api/request-card/${requestId}/review`,
          { status },
          { headers: { Authorization: `Bearer ${token}` } }
        );
      } else {
        await axios.put(
          `http://localhost:8080/api/request-card/${requestId}/status`,
          { status },
          { headers: { Authorization: `Bearer ${token}` } }
        );
      }


      alert("Status updated");
      navigate("/admin/dashboard");

    } catch (err) {
      alert(err.response?.data?.message || "Update failed");
    }
  };

  if (!request) return <h3>Loading...</h3>;

  return (
    <div className="update-req-container">
      <form className="update-req-form" onSubmit={handleSubmit}>
        <h2 className="form-title">Update Request</h2>

        <p><b>Request ID:</b> {request.requestId}</p>
        <p><b>Customer:</b> {request.customers?.customerName}</p>
        <p><b>Current Status:</b> {request.status}</p>

        <label>Update Status</label>

        <select value={status} onChange={(e) => setStatus(e.target.value)}>
          <option value="">-- Select Status --</option>
          {getNextStatus().map((s) => (
            <option key={s} value={s}>{s}</option>
          ))}
        </select>

        <button type="submit" className="submit-btn">Update</button>
      </form>
    </div>
  );
}