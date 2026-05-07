import React, { useEffect, useState } from "react";
import axios from "axios";
import "./MyActiveCards.css";

import visaLogo from "../../assets/visaLogo.svg";
import mastercard from "../../assets/mastercard.png";
import rupay from "../../assets/rupay.png";

export default function MyActiveCards() {
  const [creditCard, setCreditCard] = useState(null);
  const [debitCard, setDebitCard] = useState(null);
  const [loading, setLoading] = useState(true);

  const token = localStorage.getItem("token");

  const NETWORK_LOGOS = {
    "40": visaLogo,
    "51": mastercard,
    "60": rupay,
  };

  useEffect(() => {
    fetchActiveCards();
  }, []);

  const fetchActiveCards = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8080/api/cards/active",
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );

      const data = response.data;
      console.log("Active Cards Response:", data);

      const credit = data.find((c) => c.cardType === "CREDIT") || null;
      const debit = data.find((c) => c.cardType === "DEBIT") || null;

      setCreditCard(credit);
      setDebitCard(debit);
    } catch (error) {
      console.error("Error loading active cards:", error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div className="card-page-wrapper">
        <h3>Loading active cards...</h3>
      </div>
    );
  }

  return (
    <div className="active-card-wrapper">
      <div className="active-card-section">
          <h4>Active Credit Card</h4>
        {creditCard ? (
          <div
            className="customer-card"
            style={{
              background: creditCard.cardColourFront,
              color: creditCard.textColour,
            }}
          >
            <div className="chip">
              <div className="chip-inner">
                <div className="line v1"></div>
                <div className="line v2"></div>
                <div className="line h1"></div>
                <div className="line h2"></div>
              </div>
            </div>

            <img
              src={NETWORK_LOGOS[creditCard.binNumber]}
              alt="network"
              className="card-logo"
            />

            <div className="card-number">{creditCard.maskedNumber}</div>

            <div className="card-footer">
              <div className="card-holder">
                <label>Card Holder</label>
                <span>{creditCard.customerName}</span>
              </div>
              <div className="card-expiry">
                <label>Expiry</label>
                <span>{creditCard.expiry}</span>
              </div>
            </div>
          </div>
        ) : (
          <p className="no-cards">No active credit card</p>
        )}
      </div>

      <div className="active-card-section">
          <h4>Active Debit Card</h4>
        {debitCard ? (
          <div
            className="customer-card"
            style={{
              background: debitCard.cardColourFront,
              color: debitCard.textColour,
            }}
          >
            <div className="chip">
              <div className="chip-inner">
                <div className="line v1"></div>
                <div className="line v2"></div>
                <div className="line h1"></div>
                <div className="line h2"></div>
              </div>
            </div>

            <img
              src={NETWORK_LOGOS[debitCard.binNumber]}     // FIXED
              alt="network"
              className="card-logo"
            />

            <div className="card-number">{debitCard.maskedNumber}</div>

            <div className="card-footer">
              <div className="card-holder">
                <label>Card Holder</label>
                <span>{debitCard.customerName}</span>
              </div>
              <div className="card-expiry">
                <label>Expiry</label>
                <span>{debitCard.expiry}</span>
              </div>
            </div>
          </div>
        ) : (
          <p className="no-cards">No active debit card</p>
        )}
      </div>
    </div>
  );
}