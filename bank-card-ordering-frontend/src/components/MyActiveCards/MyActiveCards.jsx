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

  const [creditDesign, setCreditDesign] = useState(null);
  const [debitDesign, setDebitDesign] = useState(null);

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
        { headers: { Authorization: `Bearer ${token}` } }
      );

      const data = response.data || [];

      const credit = data.find((c) => c.cardType === "CREDIT") || null;
      const debit = data.find((c) => c.cardType === "DEBIT") || null;

      setCreditCard(credit);
      setDebitCard(debit);

      if (credit) fetchVariant(credit.cardVariantId, true);
      if (debit) fetchVariant(debit.cardVariantId, false);

    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const fetchVariant = async (variantId, isCredit) => {
    const variantRes = await axios.get(
      `http://localhost:8080/api/cards/variant/${variantId}`,
      { headers: { Authorization: `Bearer ${token}` } }
    );

    const v = variantRes.data;

    const design = {
      front: v.cardColourFront,
      chip: v.chipColour,
      text: v.textColour,
    };

    if (isCredit) setCreditDesign(design);
    else setDebitDesign(design);
  };

  const getCardLogo = (bin) => {
    return bin ? NETWORK_LOGOS[bin.toString().slice(0, 2)] : null;
  };

  if (loading) return <div className="active-loading">Loading...</div>;

  return (
    <div className="active-container">
      <div className="section">
        <h3 className="section-title">Active Credit Card</h3>
        {creditCard ? (
          <div
            className="card-ui"
            style={{
              background: creditDesign?.front,
              color: creditDesign?.text,
            }}
          >
            <div className="chip-ui" style={{ background: creditDesign?.chip }}></div>
            <img src={getCardLogo(creditCard.binNumber)} alt="" className="card-logo" />
            <div className="number">{creditCard.maskedNumber}</div>
            <div className="details">
              <div>
                <label>Holder</label>
                <span>{creditCard.customerName}</span>
              </div>
              <div>
                <label>Expiry</label>
                <span>{creditCard.expiry}</span>
              </div>
            </div>
          </div>
        ) : (
          <p className="empty">No Active Credit Card</p>
        )}
      </div>
      <div className="section">
        <h3 className="section-title">Active Debit Card</h3>
        {debitCard ? (
          <div
            className="card-ui"
            style={{
              background: debitDesign?.front,
              color: debitDesign?.text,
            }}
          >
            <div className="chip-ui" style={{ background: debitDesign?.chip }}></div>
            <img src={getCardLogo(debitCard.binNumber)} alt="" className="card-logo" />
            <div className="number">{debitCard.maskedNumber}</div>
            <div className="details">
              <div>
                <label>Holder</label>
                <span>{debitCard.customerName}</span>
              </div>
              <div>
                <label>Expiry</label>
                <span>{debitCard.expiry}</span>
              </div>
            </div>
          </div>
        ) : (
          <p className="empty">No Active Debit Card</p>
        )}
      </div>
    </div>
  );
}