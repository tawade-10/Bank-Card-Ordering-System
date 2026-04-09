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
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      setCards(response.data);
    } catch (error) {
      console.error("Error loading cards:", error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <div className="mycards-wrapper"><h3>Loading cards...</h3></div>;
  }

  if (cards.length === 0) {
    return (
      <div className="mycards-wrapper">
        <h2 className="full-title">My Cards</h2>
        <p className="no-cards">No active cards available</p>
      </div>
    );
  }

  return (
    <div className="mycards-wrapper">

      <h2 className="full-title">My Cards</h2>

      <div className="cards-container-full">
        {cards.map((card) => (
          <div
            key={card.cardId}
            className={`card-box ${card.cardType.toLowerCase()}`}
          >
            <div className="card-number">**** **** **** {card.lastFourDigits}</div>
            <div className="card-type">
              {card.cardType} | {card.cardVariant}
            </div>
            <div className="card-expiry">Expires: {card.expiryMonth}/{card.expiryYear}</div>
          </div>
        ))}
      </div>

      <button className="details-btn">View Card Details</button>

    </div>
  );
}