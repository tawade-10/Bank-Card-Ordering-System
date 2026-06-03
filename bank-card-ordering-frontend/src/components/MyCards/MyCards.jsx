import React, { useEffect, useState } from "react";
import axios from "axios";
import "./MyCards.css";
import { useNavigate } from "react-router-dom";
import BackButton from "../BackButton/BackButton";

import visaLogo from "../../assets/cards/visaLogo.svg";
import mastercard from "../../assets/cards/mastercard.png";
import rupay from "../../assets/cards/rupay.png";

import GoldChip from "../../assets/chips/GoldChip.png";
import ShinyChip from "../../assets/chips/ShinyChip.png";
import SilverChip from "../../assets/chips/SilverChip.png";

export default function MyCards() {
  const [cards, setCards] = useState([]);
  const [loading, setLoading] = useState(true);
  const [expandedCard, setExpandedCard] = useState(null);

  const token = localStorage.getItem("token");
  const navigate = useNavigate();

  const NETWORK_LOGOS = {
    VISA: visaLogo,
    MASTERCARD: mastercard,
    RUPAY: rupay,
  };

  const CHIP_IMAGES = {
    GoldChip,
    SilverChip,
    ShinyChip,
  };

  const getChip = (chipName) => {
    if (!chipName) return null;
    const clean = chipName.replace(".png", "").trim();
    return CHIP_IMAGES[clean] || null;
  };

  useEffect(() => {
    fetchMyCards();
  }, []);

  const fetchMyCards = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8080/api/cards/my-cards",
        { headers: { Authorization: `Bearer ${token}` } }
      );

      const baseCards = response.data;

      const cardsWithDesign = await Promise.all(
        baseCards.map(async (card) => {
          try {
            const res = await axios.get(
              `http://localhost:8080/api/cards/variant/${card.cardVariantId}`,
              { headers: { Authorization: `Bearer ${token}` } }
            );

            return { ...card, ...res.data };
          } catch {
            return card;
          }
        })
      );

      setCards(cardsWithDesign);
    } catch (error) {
      console.error("Error loading cards:", error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <div className="cards-loading">Loading cards...</div>;

  return (
    <div className="cards-page">

      {/* BACK BUTTON */}
      <div className="back-btn-normal">
        <BackButton />
      </div>

      {/* EMPTY STATE */}
      {cards.length === 0 ? (
        <div className="empty-cards-state">
          <div className="empty-card-icon">💳</div>
          <h2>No Cards Yet</h2>
          <p>You don’t have any active credit or debit cards.</p>
          <p>Once your request is approved, your cards will appear here.</p>

          <button
            className="empty-action-btn"
            onClick={() => navigate("/request-card")}
          >
            Request a Card
          </button>
        </div>
      ) : (
        <div className="cards-wrapper">
          <div className="cards-layout">

            {/* CREDIT SECTION */}
            <div className="card-column">
              <h2 className="column-title">Credit Cards</h2>

              {cards
                .filter((c) => c.cardType === "CREDIT")
                .map((card) => (
                  <div key={card.cardId} className="gpay-row">

                    <div
                      className="gpay-card-item"
                      onClick={() =>
                        setExpandedCard(
                          expandedCard === card.cardId ? null : card.cardId
                        )
                      }
                    >
                      <img
                        src={NETWORK_LOGOS[card.networkName]}
                        className="gpay-thumb"
                        alt="thumb"
                      />

                      <div>
                        <div className="gpay-title">
                          {card.bankName}{card.maskedNumber}
                        </div>
                        <div className="gpay-sub">
                          {card.networkName} card
                        </div>
                        {card.isDefault && (
                          <div className="gpay-default">
                            Default Tap & Pay
                          </div>
                        )}
                      </div>
                    </div>

                    {expandedCard === card.cardId && (
                      <div className="expanded-card-area">
                        <div className="flip-container">
                          <div className="flip-card">

                            {/* FRONT */}
                            <div
                              className="card front-side"
                              style={{
                                background: card.cardColourFront,
                                color: card.textColour,
                              }}
                            >
                              <header className="front-header">
                                <img
                                  src={NETWORK_LOGOS[card.networkName]}
                                  className="network-logo"
                                  alt="network"
                                />
                                <img
                                  className="chip-image"
                                  src={getChip(card.chipImage)}
                                  alt="chip"
                                />
                              </header>

                              <div className="card-number-real">
                                {card.maskedNumber}
                              </div>

                              <div className="card-bottom-row">
                                <div className="holder-info">
                                  <label>Holder Name</label>
                                  <div className="holder-name">
                                    {card.customerName}
                                  </div>
                                </div>

                                <div className="expiry-info">
                                  <label>Valid Thru</label>
                                  <div className="expiry-date">
                                    {card.expiry}
                                  </div>
                                </div>
                              </div>
                            </div>

                            {/* BACK */}
                            <div
                              className="card back-side"
                              style={{ background: card.cardColourBack }}
                            >
                              <div className="magnetic-strip-top"></div>

                              <div className="signature">
                                <i>{card.cvv || "XXX"}</i>
                              </div>

                              <h5 className="description">
                                This card is property of the issuing bank.
                              </h5>
                            </div>

                          </div>
                        </div>
                      </div>
                    )}
                  </div>
                ))}
            </div>

            {/* DEBIT SECTION */}
            <div className="card-column">
              <h2 className="column-title">Debit Cards</h2>

              {cards
                .filter((c) => c.cardType === "DEBIT")
                .map((card) => (
                  <div key={card.cardId} className="gpay-row">

                    <div
                      className="gpay-card-item"
                      onClick={() =>
                        setExpandedCard(
                          expandedCard === card.cardId ? null : card.cardId
                        )
                      }
                    >
                      <img
                        src={NETWORK_LOGOS[card.networkName]}
                        className="gpay-thumb"
                        alt="thumb"
                      />

                      <div>
                        <div className="gpay-title">
                          {card.bankName}{card.maskedNumber}
                        </div>
                        <div className="gpay-sub">
                          {card.networkName} card
                        </div>
                      </div>
                    </div>

                    {expandedCard === card.cardId && (
                      <div className="expanded-card-area">
                        <div className="flip-container">
                          <div className="flip-card">

                            {/* FRONT */}
                            <div
                              className="card front-side"
                              style={{
                                background: card.cardColourFront,
                                color: card.textColour,
                              }}
                            >
                              <header className="front-header">
                                <img
                                  src={NETWORK_LOGOS[card.networkName]}
                                  className="network-logo"
                                  alt="network"
                                />
                                <img
                                  className="chip-image"
                                  src={getChip(card.chipImage)}
                                  alt="chip"
                                />
                              </header>

                              <div className="card-number-real">
                                {card.maskedNumber}
                              </div>

                              <div className="card-bottom-row">
                                <div className="holder-info">
                                  <label>Holder Name</label>
                                  <div className="holder-name">
                                    {card.customerName}
                                  </div>
                                </div>

                                <div className="expiry-info">
                                  <label>Valid Thru</label>
                                  <div className="expiry-date">
                                    {card.expiry}
                                  </div>
                                </div>
                              </div>
                            </div>

                            {/* BACK */}
                            <div
                              className="card back-side"
                              style={{ background: card.cardColourBack }}
                            >
                              <div className="magnetic-strip-top"></div>

                              <div className="signature">
                                <i>{card.cvv || "XXX"}</i>
                              </div>

                              <h5 className="description">
                                This card is property of the issuing bank.
                              </h5>
                            </div>

                          </div>
                        </div>
                      </div>
                    )}
                  </div>
                ))}
            </div>

          </div>
        </div>
      )}
    </div>
  );
}