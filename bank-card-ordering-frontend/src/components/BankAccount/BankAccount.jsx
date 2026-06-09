import React, { useState } from "react";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";
import "./BankAccount.css";

export default function BankAccount() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    bankId: "",
    branchId: "",
    accountTypeId: ""
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const clearForm = () => {
    setFormData({
      bankId: "",
      branchId: "",
      accountTypeId: ""
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!formData.bankId || !formData.branchId || !formData.accountTypeId) {
      toast.error("Please fill all fields!");
      return;
    }

    const body = {
      bankId: Number(formData.bankId),
      branchId: Number(formData.branchId),
      accountTypeId: Number(formData.accountTypeId)
    };

    try {
      const response = await fetch(
        "http://localhost:8080/api/account/create-request",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + localStorage.getItem("token")
          },
          body: JSON.stringify(body)
        }
      );

      if (response.ok) {
        clearForm();
//         toast.success("Account request submitted!");
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
        <div className="form-header">Request Bank Account</div>

        <form onSubmit={handleSubmit}>
          {/* BANK TYPE */}
          <label className="label">Select Bank:</label>
          <div className="radio-group">
            <label className="radio-box">
              <input
                type="radio"
                name="bankId"
                value="1"
                onChange={handleChange}
                checked={formData.bankId === "1"}
              />
              SBI
            </label>

            <label className="radio-box">
              <input
                type="radio"
                name="bankId"
                value="2"
                onChange={handleChange}
                checked={formData.bankId === "2"}
              />
              HDFC
            </label>

            <label className="radio-box">
              <input
                type="radio"
                name="bankId"
                value="3"
                onChange={handleChange}
                checked={formData.bankId === "3"}
              />
              ICICI
            </label>
          </div>

          {/* BRANCH */}
          <label className="label">Select Branch:</label>
          <div className="radio-group">
            <label className="radio-box">
              <input
                type="radio"
                name="branchId"
                value="1"
                onChange={handleChange}
                checked={formData.branchId === "1"}
              />
              Mumbai Main
            </label>

            <label className="radio-box">
              <input
                type="radio"
                name="branchId"
                value="2"
                onChange={handleChange}
                checked={formData.branchId === "2"}
              />
              Pune
            </label>

            <label className="radio-box">
              <input
                type="radio"
                name="branchId"
                value="3"
                onChange={handleChange}
                checked={formData.branchId === "3"}
              />
              Thane
            </label>
          </div>

          {/* ACCOUNT TYPE */}
          <label className="label">Account Type:</label>
          <div className="radio-group">
            <label className="radio-box">
              <input
                type="radio"
                name="accountTypeId"
                value="1"
                onChange={handleChange}
                checked={formData.accountTypeId === "1"}
              />
              Savings
            </label>

            <label className="radio-box">
              <input
                type="radio"
                name="accountTypeId"
                value="2"
                onChange={handleChange}
                checked={formData.accountTypeId === "2"}
              />
              Current
            </label>
          </div>

          <div className="button-row">
            <button type="submit" className="submit-btn">
              Submit
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}