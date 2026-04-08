import React from "react";
import "./Header.css";
import { useNavigate, useLocation } from "react-router-dom";

export default function Header() {
  const navigate = useNavigate();
  const location = useLocation();

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/");
  };

  const isLoginPage = location.pathname === "/";

  return (
    <div className="header-bar">
      <div className="logo">Bank Name</div>
      {!isLoginPage && localStorage.getItem("token") && (
        <button className="logout-btn" onClick={handleLogout}>
          Logout
        </button>
      )}
    </div>
  );
}