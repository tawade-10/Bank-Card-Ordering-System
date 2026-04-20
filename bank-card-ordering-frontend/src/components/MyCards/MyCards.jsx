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
          <div className="display-card" key={card.cardId}>

            {/* Chip */}
            <div className="chip-small"></div>
            <div className="display-number">{card.maskedNumber}</div>
            <div className="display-variant">
                <span>Card Holder</span>
              <div>{card.customerName}</div>
            </div>
            <div className="display-expiry">
              <span>Expiry</span>
              <div>{card.expiry}</div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}