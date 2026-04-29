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
  const [number, setNumber] = useState("");
  const [expiration, setExpiration] = useState("__/__");
  const [cvc, setCvc] = useState("");

  const [cardTypeId, setCardTypeId] = useState(null);
  const [cardVariantId, setCardVariantId] = useState(null);
  const [cardNetworkId, setNetworkId] = useState(null);
  const [binNumber, setBinNumber] = useState("");

  const [loading, setLoading] = useState(true);

  const [design, setDesign] = useState({
    front: "#000",
    back: "#000",
    chip: "#fff",
    text: "#fff",
  });

  const handleGoBack = () => navigate(-1);

  useEffect(() => {
    fetchRequestDetails();
  }, []);

  // Fetch request + card design + BIN number
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
      setNetworkId(req.cardNetworkId);

      setDesign({
        front: req.cardColourFront,
        back: req.cardColourBack,
        chip: req.chipColour,
        text: req.textColour,
      });

      // Fetch BIN from backend
      const binRes = await axios.get(
        `http://localhost:8080/api/request-card/network/${req.cardNetworkId}`,
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        }
      );

      const bin = binRes.data.bin;
      setBinNumber(bin);

      // Pre-populate card number with BIN prefix
      setNumber(formatCardNumber(bin));

    } catch (err) {
      console.error(err);
      alert("Failed to load request");
    } finally {
      setLoading(false);
    }
  };

  // Format number (1111222233334444 → 1111 2222 3333 4444)
  const formatCardNumber = (num) => {
    return num.replace(/\D/g, "").replace(/(.{4})/g, "$1 ").trim();
  };

  // Handle card number entry BUT BIN prefix cannot be removed
  const handleNumberChange = (e) => {
    const input = e.target.value.replace(/\s/g, "");

    if (!input.startsWith(binNumber)) {
      return; // BLOCK USER from deleting bin prefix
    }

    let cleaned = input.substring(0, 16);
    setNumber(formatCardNumber(cleaned));
  };

  const handleExpiryChange = (e) => {
    let input = e.target.value.replace(/\D/g, "").substring(0, 4);

    if (input.length >= 3) {
      input = input.substring(0, 2) + "/" + input.substring(2);
    }

    setExpiration(input || "__/__");
  };

  const turnAround = useCallback(() => {
    if (!flipper.current) return;

    flipper.current.style.transform =
      side === "front" ? "rotateY(180deg)" : "rotateY(0deg)";

    setSide((prev) => (prev === "front" ? "back" : "front"));
  }, [side]);

  const handleSubmit = async () => {
    try {
      const cleanCardNumber = number.replace(/\s/g, "");

      if (cleanCardNumber.length !== 16) {
        alert("Card number must be 16 digits");
        return;
      }

      if (cvc.length < 3) {
        alert("Invalid CVC");
        return;
      }

      const payload = {
        requestId: parseInt(requestId),
        cardNumber: cleanCardNumber,
        cvv: cvc,
        expiry: expiration,
        cardType: cardTypeId,
        cardVariant: cardVariantId,
        cardNetwork: cardNetworkId,
      };

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
            <CardNumber>{number || "____ ____ ____ ____"}</CardNumber>
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
        <Input
          placeholder="Card Number"
          value={number}
          onChange={handleNumberChange}
        />

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