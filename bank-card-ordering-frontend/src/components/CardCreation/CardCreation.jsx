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
  CVC,
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

  function generateCardNumber(bin) {
    bin = bin.replace(/\s/g, "");
    const remaining = 16 - bin.length;
    let randomDigits = "";

    for (let i = 0; i < remaining; i++) {
      randomDigits += Math.floor(Math.random() * 10);
    }

    const finalNumber = bin + randomDigits;
    return finalNumber.replace(/(.{4})/g, "$1 ").trim();
  }

  function generateCvvNumber() {
    let cvv = "";
    for (let i = 0; i < 3; i++) {
      cvv += Math.floor(Math.random() * 10);
    }
    return cvv;
  }

  function generateExpiryDate() {
    const today = new Date();
    const expiryYear = today.getFullYear() + 5;
    const month = today.getMonth() + 1;

    const formattedMonth = month < 10 ? "0" + month : month.toString();
    const formattedYear = expiryYear.toString().slice(-2);

    return `${formattedMonth}/${formattedYear}`;
  }

  const fetchRequestDetails = async () => {
    try {
      const token = localStorage.getItem("token");

      const reqRes = await axios.get(
        `http://localhost:8080/api/request-card/${requestId}`,
        { headers: { Authorization: `Bearer ${token}` } }
      );

      const req = reqRes.data;

      setCardTypeId(req.cardTypeId);
      setCardVariantId(req.cardVariantId);
      setNetworkId(req.cardNetworkId);

      const customerRes = await axios.get(
        `http://localhost:8080/api/customers/${req.customerId}`,
        { headers: { Authorization: `Bearer ${token}` } }
      );

      const customer = customerRes.data;
      setName(customer.customerName);

      const variantRes = await axios.get(
        `http://localhost:8080/api/cards/variant/${req.cardVariantId}`,
        { headers: { Authorization: `Bearer ${token}` } }
      );

      const variant = variantRes.data;

      setDesign({
        front: variant.cardColourFront,
        back: variant.cardColourBack,
        chip: variant.chipColour,
        text: variant.textColour,
      });

      // Fetch BIN number
      const binRes = await axios.get(
        `http://localhost:8080/api/request-card/network/${req.cardNetworkId}`,
        { headers: { Authorization: `Bearer ${token}` } }
      );

      const bin = binRes.data.bin;
      setBinNumber(bin);

      // Generate card number
      const generatedCardNum = generateCardNumber(bin);
      setNumber(generatedCardNum);

      // Generate CVC
      const generatedCVC = generateCvvNumber();
      setCvc(generatedCVC);

      const generatedExpiry = generateExpiryDate();
      setExpiration(generatedExpiry);

    } catch (err) {
      console.error(err);
      alert("Failed to load request details");
    } finally {
      setLoading(false);
    }
  };

  const formatCardNumber = (num) => {
    return num.replace(/\D/g, "").replace(/(.{4})/g, "$1 ").trim();
  };

  const handleNumberChange = (e) => {
    const input = e.target.value.replace(/\s/g, "");

    if (!input.startsWith(binNumber)) return;

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

      if (cvc.length !== 3) {
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
        headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
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
          {/* FRONT SIDE */}
          <CardFront style={{ background: design.front, color: design.text }}>
            <Chip style={{ background: design.chip }} />

            <Name>{name || "CARD HOLDER"}</Name>

            <CardNumber>{number || "____ ____ ____ ____"}</CardNumber>

            <Expiration>{expiration}</Expiration>
          </CardFront>

          {/* BACK SIDE */}
          <CardBack style={{ background: design.back, color: design.text }}>
            <BlackStripe>
              <CVC>{cvc || "***"}</CVC>
            </BlackStripe>
          </CardBack>
        </Flipper>
      </Card>

      <CardForm>
        <Input
          value={number}
          onChange={handleNumberChange}
          placeholder="Card Number"
        />

        <Input
          value={name}
          onChange={(e) => setName(e.target.value)}
          placeholder="Card Holder Name"
        />

        <FormRow>
          <Input
            value={expiration}
            onChange={handleExpiryChange}
            placeholder="MM/YY"
          />

          <Input
            value={cvc}
            onChange={(e) => setCvc(e.target.value)}
            placeholder="CVC"
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