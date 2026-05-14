import React, { useState } from "react";
import axios from "axios";
import "./ForgetPassword.css";
import { IoArrowBack } from "react-icons/io5";
import { useNavigate } from "react-router-dom";

export default function ForgetPassword() {
  const [email, setEmail] = useState("");
  const [msg, setMsg] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMsg("");

    try {
      const res = await axios.post("http://localhost:8080/api/auth/forgot-password", {
        email,
      });

      setMsg("Reset password link has been sent to your email.");
    } catch (error) {
      setMsg("Email not found or something went wrong.");
    }
  };

  return (
    <div className="fp-container">
      <div className="fp-card">

        <button className="fp-back-btn" onClick={() => navigate("/login")}>
          <IoArrowBack size={22} />
        </button>

        <h2 className="fp-title">Forgot Password?</h2>
{/*         <p className="fp-subtitle"> */}
{/*           Enter your registered email and we will send you the instructions to reset your password. */}
{/*         </p> */}

        <form className="fp-form" onSubmit={handleSubmit}>
          <label className="fp-label">Email Address</label>
          <input
            type="email"
            className="fp-input"
            placeholder="example@gmail.com"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />

          <button type="submit" className="fp-submit">
            Send Reset Link
          </button>
        </form>

        {msg && <p className="fp-message">{msg}</p>}
      </div>
    </div>
  );
}