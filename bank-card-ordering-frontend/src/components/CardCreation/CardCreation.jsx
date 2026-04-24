import React, { useRef, useState, useCallback, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";
import { IoArrowBack } from "react-icons/io5";

import {
  Card,
  Container,
  Flipper,
  CardFront,
  CardBack,
  CardForm,
  Input,
  FormRow,
  Name,
  CardNumber,
  Expiration,
  Chip,
  BlackStripe,
  CVC
} from "./style";

export default function CardCreation() {
  const { requestId } = useParams();
  const navigate = useNavigate();

  const flipper = useRef(null);
  const [side, setSide] = useState("front");

  const [name, setName] = useState("");
  const [number, setNumber] = useState("____ ____ ____ ____");
  const [expiration, setExpiration] = useState("__/__");
  const [cvc, setCvc] = useState("");

  const [cardTypeId, setCardTypeId] = useState(null);
  const [cardVariantId, setCardVariantId] = useState(null);

  const [loading, setLoading] = useState(true);

  const [design, setDesign] = useState({
    front: "#000",
    back: "#000",
    chip: "#fff",
    text: "#fff"
  });

  const handleGoBack = () => navigate(-1);

  useEffect(() => {
    fetchRequestDetails();
  }, []);

  const fetchRequestDetails = async () => {
    try {
      const res = await axios.get(
        `http://localhost:8080/api/request-card/${requestId}`,
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        }
      );

      const req = res.data;

      setCardTypeId(req.cardTypeId);
      setCardVariantId(req.cardVariantId);

      setDesign({
        front: req.cardColourFront,
        back: req.cardColourBack,
        chip: req.chipColour,
        text: req.textColour,
      });

    } catch (err) {
      console.error(err);
      alert("Failed to load request");
    } finally {
      setLoading(false);
    }
  };

  const turnAround = useCallback(() => {
    if (!flipper.current) return;

    flipper.current.style.transform =
      side === "front" ? "rotateY(180deg)" : "rotateY(0deg)";

    setSide((prev) => (prev === "front" ? "back" : "front"));
  }, [side]);

  const handleNumberChange = (e) => {
    let input = e.target.value.replace(/\D/g, "").substring(0, 16);
    let formatted = input.replace(/(.{4})/g, "$1 ").trim();
    setNumber(formatted || "____ ____ ____ ____");
  };

  const handleExpiryChange = (e) => {
    let input = e.target.value.replace(/\D/g, "").substring(0, 4);

    if (input.length >= 3) {
      input = input.substring(0, 2) + "/" + input.substring(2);
    }

    setExpiration(input || "__/__");
  };

  const handleSubmit = async () => {
    try {
      if (!cardTypeId || !cardVariantId) {
        alert("Request design not loaded yet");
        return;
      }

      const cleanCardNumber = number.replace(/\s/g, "");

      if (cleanCardNumber.length !== 16) {
        alert("Invalid card number");
        return;
      }

      if (cvc.length < 3) {
        alert("Invalid CVC");
        return;
      }

      if (expiration.length !== 5) {
        alert("Invalid expiry");
        return;
      }

      const payload = {
        requestId: parseInt(requestId, 10),
        cardNumber: cleanCardNumber,
        cvv: cvc,
        expiry: expiration,
        cardType: cardTypeId,
        cardVariant: cardVariantId
      };

      console.log("FINAL PAYLOAD:", payload);

      await axios.post("http://localhost:8080/api/cards/create-card", payload, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      });

      alert("Card Created Successfully!");
      navigate("/admin/dashboard");

    } catch (err) {
      console.error(err);
      alert("Error creating card");
    }
  };

  return (
    <Container style={{ position: "relative" }}>
      <button
        onClick={handleGoBack}
        style={{ position: "absolute", top: "20px", left: "20px" }}
      >
        <IoArrowBack /> Back
      </button>
      <Card>
        <Flipper ref={flipper}>
          <CardFront style={{ background: design.front, color: design.text }}>
            <Chip style={{ background: design.chip }} />
            <Name>{name || "CARD HOLDER"}</Name>
            <CardNumber>{number}</CardNumber>
            <Expiration>{expiration}</Expiration>
          </CardFront>

          <CardBack style={{ background: design.back, color: design.text }}>
            <BlackStripe>
              <CVC>{cvc || "***"}</CVC>
            </BlackStripe>
          </CardBack>
        </Flipper>
      </Card>

      {/* FORM */}
      <CardForm>
        <Input placeholder="Card Number" onChange={handleNumberChange} />

        <Input
          placeholder="Card Holder Name"
          onChange={(e) => setName(e.target.value)}
        />

        <FormRow>
          <Input placeholder="MM/YY" onChange={handleExpiryChange} />

          <Input
            placeholder="CVC"
            onChange={(e) => setCvc(e.target.value)}
            onFocus={turnAround}
            onBlur={turnAround}
          />
        </FormRow>

        <button onClick={handleSubmit} disabled={loading}>
          {loading ? "Loading..." : "Create Card"}
        </button>
      </CardForm>
    </Container>
  );
}