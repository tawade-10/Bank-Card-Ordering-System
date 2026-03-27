import React, { useState } from "react";
import { toast } from "react-toastify";
import { useNavigate } from 'react-router-dom';
import "./RequestNewCard.css";

export default function RequestNewCard() {

  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    cardType: "",
    cardVariant: "",
    reason: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const clearForm = () => {
    setFormData({
      cardType: "",
      cardVariant: "",
      reason: "",
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const body = {
      cardType: formData.cardType,
      cardVariant: formData.cardVariant,
      reason: formData.reason,
    };

    try {
      const response = await fetch("http://localhost:8080/api/request-card/create-request", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(body),
      });

      if (response.ok) {
        const textResponse = await response.text();    // because backend returns String

        toast.success("Card Request Submitted!");

        clearForm();

        setTimeout(() => {
          navigate("/dashboard");       // redirect to different page
        }, 1200);

      } else {
        toast.error("Request Cancelled!");
      }
    } catch (error) {
      toast.error("Backend not reachable!");
    }
  };

  return (
    <div className="container">
      <div className="form-container">
        <div className="form-header">Request New Card</div>

        <form onSubmit={handleSubmit}>

          <label className="label">Select Card Type:</label>
          <div className="radio-group">
            <label className="radio-box">
              <input
                type="radio"
                name="cardType"
                value="DEBIT"
                onChange={handleChange}
                checked={formData.cardType === "DEBIT"}
              />
              Debit Card
            </label>

            <label className="radio-box">
              <input
                type="radio"
                name="cardType"
                value="CREDIT"
                onChange={handleChange}
                checked={formData.cardType === "CREDIT"}
              />
              Credit Card
            </label>
          </div>

          <label className="label">Card Variant:</label>
          <div className="radio-group">
            <label className="radio-box">
              <input
                type="radio"
                name="cardVariant"
                value="CLASSIC"
                onChange={handleChange}
                checked={formData.cardVariant === "CLASSIC"}
              />
              Classic
            </label>

            <label className="radio-box">
              <input
                type="radio"
                name="cardVariant"
                value="GOLD"
                onChange={handleChange}
                checked={formData.cardVariant === "GOLD"}
              />
              Gold
            </label>

            <label className="radio-box">
              <input
                type="radio"
                name="cardVariant"
                value="PLATINUM"
                onChange={handleChange}
                checked={formData.cardVariant === "PLATINUM"}
              />
              Platinum
            </label>
          </div>

          <label className="label">Reason for Request:</label>
          <select
            id="reason"
            name="reason"
            className="styled-select"
            value={formData.reason}
            onChange={handleChange}
          >
            <option value="">Select a Reason--</option>
            <option value="NEW_CARD">New Card</option>
            <option value="LOST_CARD">Lost Card</option>
            <option value="REPLACEMENT">Replacement</option>
            <option value="UPGRADE">Upgrade</option>
            <option value="DAMAGED_CARD">Damaged Card</option>
          </select>

          <div className="button-row">
            <button type="submit" className="submit-btn">Submit</button>
          </div>

        </form>
      </div>
    </div>
  );
}