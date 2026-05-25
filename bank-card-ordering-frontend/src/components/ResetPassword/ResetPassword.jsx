import React, { useState } from "react";
import axios from "axios";
import "./ResetPassword.css";
import { useNavigate, useSearchParams } from "react-router-dom";

export default function ResetPassword() {
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [msg, setMsg] = useState("");
  const [loading, setLoading] = useState(false);

  const [searchParams] = useSearchParams();
  const token = searchParams.get("token");

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMsg("");

    if (password !== confirmPassword) {
      setMsg("Passwords do not match!");
      return;
    }

    setLoading(true);

    try {
      await axios.post(
        `http://localhost:8080/api/reset-password?token=${token}&newPassword=${password}`
      );
      setMsg("Password reset successful!");
      setTimeout(() => navigate("/"), 1500);
    } catch (error) {
      setMsg("Invalid or expired token!");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="rp-container">
      <div className="rp-card">
        <h2 className="rp-title">Reset Password</h2>

        <form className="rp-form" onSubmit={handleSubmit}>
          <label className="rp-label">New Password</label>
          <input
            type="password"
            className="rp-input"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />

          <label className="rp-label">Confirm Password</label>
          <input
            type="password"
            className="rp-input"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
            required
          />

          <button type="submit" className="rp-submit" disabled={loading}>
            {loading ? "Updating..." : "Reset Password"}
          </button>
        </form>

        {msg && <p className="rp-message">{msg}</p>}
      </div>
    </div>
  );
}