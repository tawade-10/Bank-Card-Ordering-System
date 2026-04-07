import React from "react";
import "./MyCards.css";

export default function MyCards({ layout = "dashboard" }) {
  return (
    <div className={`mycards-wrapper ${layout === "full" ? "full-layout" : ""}`}>

      {layout === "full" && <h2 className="full-title">My Cards</h2>}

      <div className={`cards-container ${layout}`}>
        {/* Credit Card */}
        <div className="card-box credit">
          <div className="card-number">**** **** **** 1234</div>
          <div className="card-type">Credit Card | Platinum</div>
          <div className="card-expiry">Expires: 12/26</div>
        </div>

        {/* Debit Card */}
        <div className="card-box debit">
          <div className="card-number">**** **** **** 5678</div>
          <div className="card-type">Debit Card | Gold</div>
          <div className="card-expiry">Expires: 09/24</div>
        </div>
      </div>

      {layout === "full" && (
        <button className="details-btn">View Card Details</button>
      )}
    </div>
  );
}