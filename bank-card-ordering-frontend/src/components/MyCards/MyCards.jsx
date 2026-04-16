import React, { useEffect, useState } from "react";
import axios from "axios";
import "./MyCards.css";

export default function MyCards() {
  const [cards, setCards] = useState([]);
  const [loading, setLoading] = useState(true);

  const token = localStorage.getItem("token");

  useEffect(() => {
    fetchMyCards();
  }, []);

  const fetchMyCards = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/cards/my-cards", {
        headers: { Authorization: `Bearer ${token}` },
      });
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
{/*       <h2 className="page-title">My Active Cards</h2> */}
      <div className="cards-grid">
        {cards.map((card) => (
          <div className="display-card" key={card.cardId}>
            <div className="chip-small"></div>
            <div className="display-number">
              {card.cardNumber}
            </div>

            <div className="display-row">
              <span>Type:</span> {card.cardType}
            </div>

            <div className="display-row">
              <span>Variant:</span> {card.cardVariant}
            </div>

            <div className="display-row">
              <span>Expiry:</span> {card.expiryDate}
            </div>

            <div className="display-row">
              <span>Customer ID:</span> {card.customerId}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}