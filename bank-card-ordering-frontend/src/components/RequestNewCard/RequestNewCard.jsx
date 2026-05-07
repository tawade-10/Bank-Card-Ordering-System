import React, { useState } from "react";
import { toast } from "react-toastify";
import { useNavigate } from 'react-router-dom';
import "./RequestNewCard.css";

export default function RequestNewCard() {

  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    cardTypeId: "",
    cardVariantId: "",
    cardNetworkId: "",
    reasonId: ""
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const clearForm = () => {
    setFormData({
      cardTypeId: "",
      cardVariantId: "",
      cardNetworkId: "",
      reasonId: ""
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!formData.cardTypeId || !formData.cardVariantId || !formData.reasonId) {
      toast.error("Please fill all fields!");
      return;
    }

    const body = {
      cardTypeId: Number(formData.cardTypeId),
      cardVariantId: Number(formData.cardVariantId),
      cardNetworkId: Number(formData.cardNetworkId),
      reasonId: Number(formData.reasonId)
    };

    try {
      const response = await fetch("http://localhost:8080/api/request-card/create-request", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": "Bearer " + localStorage.getItem("token")
        },
        body: JSON.stringify(body),
      });

      if (response.ok) {
        toast.success("Card Request Submitted!");
        clearForm();
        setTimeout(() => navigate("/dashboard"), 1200);
      } else {
        const errorText = await response.text();
        toast.error(errorText || "Request Failed!");
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
                name="cardTypeId"
                value="1"
                onChange={handleChange}
                checked={formData.cardTypeId === "1"}
              />
              Credit Card
            </label>

            <label className="radio-box">
              <input
                type="radio"
                name="cardTypeId"
                value="2"
                onChange={handleChange}
                checked={formData.cardTypeId === "2"}
              />
              Debit Card
            </label>

          </div>

          <label className="label">Card Variant:</label>
          <div className="radio-group">

            <label className="radio-box">
              <input
                type="radio"
                name="cardVariantId"
                value="1"
                onChange={handleChange}
                checked={formData.cardVariantId === "1"}
              />
              Classic
            </label>

            <label className="radio-box">
              <input
                type="radio"
                name="cardVariantId"
                value="2"
                onChange={handleChange}
                checked={formData.cardVariantId === "2"}
              />
              Gold
            </label>

            <label className="radio-box">
              <input
                type="radio"
                name="cardVariantId"
                value="3"
                onChange={handleChange}
                checked={formData.cardVariantId === "3"}
              />
              Platinum
            </label>

          </div>

          <label className="label">Select Card Network:</label>
          <div className="radio-group">

                      <label className="radio-box">
                        <input
                          type="radio"
                          name="cardNetworkId"
                          value="1"
                          onChange={handleChange}
                          checked={formData.cardNetworkId === "1"}
                        />
                        MasterCard
                      </label>

                      <label className="radio-box">
                        <input
                          type="radio"
                          name="cardNetworkId"
                          value="2"
                          onChange={handleChange}
                          checked={formData.cardNetworkId === "2"}
                        />
                        Visa
                      </label>

                      <label className="radio-box">
                          <input
                            type="radio"
                            name="cardNetworkId"
                            value="3"
                            onChange={handleChange}
                            checked={formData.cardNetworkId === "3"}
                          />
                          Rupay
                      </label>
                    </div>

          <label className="label">Reason for Request:</label>
          <select
            id="reasonId"
            name="reasonId"
            className="styled-select"
            value={formData.reasonId}
            onChange={handleChange}
          >
            <option value="">Select a Reason--</option>
            <option value="1">New Card</option>
            <option value="2">Lost Card</option>
            <option value="3">Replacement</option>
            <option value="4">Upgrade</option>
            <option value="5">Damaged Card</option>
          </select>

          <div className="button-row">
            <button type="submit" className="submit-btn">Submit</button>
          </div>

        </form>
      </div>
    </div>
  );
}