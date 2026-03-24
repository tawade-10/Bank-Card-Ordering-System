import React from "react";
import "./Header.css";
import { useNavigate } from "react-router-dom";

export default function HeaderTabs() {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/");
  };

  return (
    <div className="header-bar">
      <div className="logo">Bank Name</div>
      {localStorage.getItem("token") && (
        <button className="logout-btn" onClick={handleLogout}>
          Logout
        </button>
      )}
    </div>
  );
}