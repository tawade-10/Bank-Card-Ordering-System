import React, { useEffect, useState } from "react";
import axios from "axios";
import "./MyCards.css";

import visaLogo from "../../assets/visaLogo.svg";
import mastercard from "../../assets/mastercard.png";
import rupay from "../../assets/rupay.png";

export default function MyCards() {
  const [cards, setCards] = useState([]);
  const [loading, setLoading] = useState(true);

  const token = localStorage.getItem("token");

  const NETWORK_LOGOS = {
    VISA: visaLogo,
    MASTERCARD: mastercard,
    RUPAY: rupay,
  };

  useEffect(() => {
    fetchMyCards();
  }, []);

  const fetchMyCards = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8080/api/cards/my-cards",
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      setCards(response.data);
    } catch (error) {
      console.error("Error loading cards:", error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div className="card-page-wrapper">
        <h3>Loading cards...</h3>
      </div>
    );
  }

  if (cards.length === 0) {
    return (
      <div className="card-page-wrapper">
        <p className="no-cards">No active cards available</p>
      </div>
    );
  }

  return (
    <div className="card-page-wrapper">
      <div className="cards-grid">
        {cards.map((card) => (
          <div
            className="customer-card"
            key={card.cardId}
            style={{
              background: card.cardColourFront,
              color: card.textColour,
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
              src={NETWORK_LOGOS[card.cardNetwork] || visaLogo}
              alt="network"
              className="card-logo"
            />

            <div className="card-number">{card.maskedNumber}</div>

            <div className="card-footer">
              <div className="card-holder">
                <label>Card Holder</label>
                <span>{card.customerName}</span>
              </div>

              <div className="card-expiry">
                <label>Expiry</label>
                <span>{card.expiry}</span>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

//in this now as we are creating different jsx for mycards and myactive cards where on Dashboard we will render MyActiveCards.jsx which will show only the current active credit and debit card, currently make the MyActiveCard.jsx first such that left side half will show the active credit card and the right hand half will show the active debit card

// .card-page-wrapper {
//   width: 100%;
//   display: flex;
//   flex-direction: column;
//   align-items: center;
//   font-family: "Poppins", sans-serif;
// }
//
// .no-cards {
//   margin-top: 50px;
//   font-size: 18px;
//   color: #666;
// }
//
// .cards-grid {
//   display: grid;
//   grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
//   gap: 25px;
//   width: 100%;
//   max-width: 1100px;
// }
//
// .customer-card {
//   height: 150px;
//   width: 320px;
//   border-radius: 12px;
//   padding: 20px;
//   position: relative;
//   overflow: hidden;
//   box-shadow: 0 8px 22px rgba(0, 0, 0, 0.25);
//   display: flex;
//   flex-direction: column;
//   transition: transform 0.25s ease, box-shadow 0.25s ease;
// }
//
// .customer-card:hover {
//   transform: translateY(-6px);
//   box-shadow: 0 14px 30px rgba(0, 0, 0, 0.28);
// }
//
// .chip {
//   position: absolute;
//   top: 20px;
//   left: 20px;
//
//   width: 60px;
//   height: 40px;
//   border-radius: 8px;
//
//   background: linear-gradient(135deg, #d4af37, #f5d76e, #d4af37);
//
//   box-shadow:
//     inset 0 0 5px rgba(0, 0, 0, 0.4),
//     0 2px 4px rgba(0, 0, 0, 0.3);
//
//   display: flex;
//   align-items: center;
//   justify-content: center;
// }
//
// .chip-inner {
//   width: 80%;
//   height: 70%;
//   position: relative;
//   border-radius: 4px;
//
//   background: linear-gradient(135deg, #c9a227, #f1c40f);
//
//   box-shadow: inset 0 0 3px rgba(0, 0, 0, 0.5);
// }
//
// .line {
//   position: absolute;
//   background: rgba(0, 0, 0, 0.5);
// }
//
// .v1 {
//   width: 2px;
//   height: 100%;
//   left: 30%;
// }
//
// .v2 {
//   width: 2px;
//   height: 100%;
//   right: 30%;
// }
//
// .h1 {
//   height: 2px;
//   width: 100%;
//   top: 30%;
// }
//
// .h2 {
//   height: 2px;
//   width: 100%;
//   bottom: 30%;
// }
//
// .card-logo {
//   position: absolute;
//   top: 15px;
//   right: 20px;
//   width: 60px;
//   height: auto;
//   opacity: 0.95;
//   pointer-events: none;
// }
//
// .card-number {
//   position: absolute;
//   bottom: 60px;
//   left: 20px;
//   font-size: 18px;
//   font-weight: bold;
//   letter-spacing: 2px;
// }
//
// .card-footer {
//   position: absolute;
//   bottom: 15px;
//   left: 20px;
//   right: 20px;
//   display: flex;
//   justify-content: space-between;
// }
//
// .card-holder label,
// .card-expiry label {
//   font-size: 12px;
//   opacity: 0.7;
// }
//
// .card-holder span,
// .card-expiry span {
//   font-size: 14px;
//   font-weight: 600;
//   display: block;
//   margin-top: 3px;
// }