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
      <div className="cards-grid">
        {cards.map((card) => (
          <div
            className="customer-card"
            key={card.cardId}
            style={{
              background: card.cardColourFront,
              color: card.textColour
            }}
          >
            <div
              className="chip"
              style={{ background: card.chipColour }}
            ></div>

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