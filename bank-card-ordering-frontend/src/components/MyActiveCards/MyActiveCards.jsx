import React, { useEffect, useState } from "react";
import axios from "axios";
import "./MyActiveCards.css";

import visaLogo from "../../assets/cards/visaLogo.svg";
import mastercard from "../../assets/cards/mastercard.png";
import rupay from "../../assets/cards/rupay.png";

import GoldChip from "../../assets/chips/GoldChip.png";
import ShinyChip from "../../assets/chips/ShinyChip.png";
import SilverChip from "../../assets/chips/SilverChip.png";

export default function MyActiveCards() {
  const [creditCard, setCreditCard] = useState(null);
  const [debitCard, setDebitCard] = useState(null);
  const [loading, setLoading] = useState(true);

  const [creditDesign, setCreditDesign] = useState(null);
  const [debitDesign, setDebitDesign] = useState(null);

  const token = localStorage.getItem("token");

  const NETWORK_LOGOS = {
    VISA: visaLogo,
    MASTERCARD: mastercard,
    RUPAY: rupay,
  };

  const CHIP_IMAGES = {
    GoldChip: GoldChip,
    SilverChip: SilverChip,
    ShinyChip: ShinyChip,
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
    try {
      const res = await axios.get(
        `http://localhost:8080/api/cards/variant/${variantId}`,
        { headers: { Authorization: `Bearer ${token}` } }
      );

      const v = res.data;

      const design = {
        front: v.cardColourFront,
        back: v.cardColourBack,
        text: v.textColour,
        chip: CHIP_IMAGES[v.chipImage.replace(".png", "")],
      };

      if (isCredit) setCreditDesign(design);
      else setDebitDesign(design);
    } catch (err) {
      console.error("Variant fetch failed", err);
    }
  };

  const getLogo = (network) => {
    return NETWORK_LOGOS[network] || null;
  };

  if (loading) return <div className="active-loading">Loading...</div>;

  return (
    <div className="active-wrapper">

      {/* CREDIT CARD */}
      <div className="active-box">
        <h3 className="title">Active Credit Card</h3>

        {creditCard ? (
          <div
            className="active-card"
            style={{
              background: creditDesign?.front,
              color: creditDesign?.text,
            }}
          >
            <div className="card-header">
              <img src={getLogo(creditCard.networkName)} alt="" className="logo" />

              <div className="chip">
                <img src={creditDesign?.chip} alt="chip" className="chip-img" />
              </div>
            </div>

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

      {/* DEBIT CARD */}
      <div className="active-box">
        <h3 className="title">Active Debit Card</h3>

        {debitCard ? (
          <div
            className="active-card"
            style={{
              background: debitDesign?.front,
              color: debitDesign?.text,
            }}
          >
            <div className="card-header">
              <img src={getLogo(debitCard.networkName)} alt="" className="logo" />

              <div className="chip">
                <img src={debitDesign?.chip} alt="chip" className="chip-img" />
              </div>
            </div>

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