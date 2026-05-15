import React, { useEffect, useState } from "react";
import axios from "axios";
import "./MyCards.css";

import visaLogo from "../../assets/cards/visaLogo.svg";
import mastercard from "../../assets/cards/mastercard.png";
import rupay from "../../assets/cards/rupay.png";

import GoldChip from "../../assets/chips/GoldChip.png";
import ShinyChip from "../../assets/chips/ShinyChip.png";
import SilverChip from "../../assets/chips/SilverChip.png";

export default function MyCards() {
  const [cards, setCards] = useState([]);
  const [loading, setLoading] = useState(true);
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

            const design = res.data;

            return {
              ...card,
              cardColourFront: design.cardColourFront,
              cardColourBack: design.cardColourBack,
              chipImage: design.chipImage,
              textColour: design.textColour,
            };
          } catch (err) {
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
  if (cards.length === 0)
    return <div className="cards-loading">No active cards available</div>;

  return (
    <div className="cards-page">
      <div className="card-column">
        <h2 className="column-title">Credit Cards</h2>
        <div className="card-list">
          {cards
            .filter((c) => c.cardType === "CREDIT")
            .map((card) => (
              <div className="flip-container" key={card.cardId}>
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

                    <div className="card-number-real">{card.maskedNumber}</div>

                    <div className="card-bottom-row">
                      <div className="holder-info">
                        <label>Holder Name</label>
                        <div className="holder-name">{card.customerName}</div>
                      </div>

                      <div className="expiry-info">
                        <label>Valid Thru</label>
                        <div className="expiry-date">{card.expiry}</div>
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
                      This card is property of the issuing bank. If found,
                      return to the nearest branch.
                    </h5>
                  </div>
                </div>
              </div>
            ))}
        </div>
      </div>

      {/* DEBIT CARDS */}
      <div className="card-column">
        <h2 className="column-title">Debit Cards</h2>

        <div className="card-list">
          {cards
            .filter((c) => c.cardType === "DEBIT")
            .map((card) => (
              <div className="flip-container" key={card.cardId}>
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

                    <div className="card-number-real">{card.maskedNumber}</div>

                    <div className="card-bottom-row">
                      <div className="holder-info">
                        <label>Cardholder Name</label>
                        <div className="holder-name">{card.customerName}</div>
                      </div>

                      <div className="expiry-info">
                        <label>Valid Thru</label>
                        <div className="expiry-date">{card.expiry}</div>
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
                      This card is property of the issuing bank. If found,
                      return to the nearest branch.
                    </h5>
                  </div>
                </div>
              </div>
            ))}
        </div>
      </div>
    </div>
  );
}


