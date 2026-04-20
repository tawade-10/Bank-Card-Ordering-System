import React, { useRef, useState, useCallback } from "react";
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
  Number,
  Expiration,
  Chip,
  BlackStripe,
  CVC
} from "./style";

export default function CardCreation() {

  const { requestId } = useParams();

  const flipper = useRef(null);
  const [side, setSide] = useState("front");

  const [name, setName] = useState("");
  const [number, setNumber] = useState("____ ____ ____ ____");
  const [expiration, setExpiration] = useState("__/__");
  const [cvc, setCvc] = useState("");

  const navigate = useNavigate();

  const handleGoBack = () => navigate(-1);

  const turnAround = useCallback(() => {
    if (!flipper.current) return;

    flipper.current.style.transform =
      side === "front" ? "rotateY(180deg)" : "rotateY(0deg)";

    setSide(prev => (prev === "front" ? "back" : "front"));
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
      const cleanCardNumber = number.replace(/\s/g, "");
      const cleanExpiry = expiration;

      if (cleanCardNumber.length !== 16) {
        alert("Invalid Card Number");
        return;
      }

      if (!cvc || cvc.length < 3) {
        alert("Invalid CVV");
        return;
      }

      if (cleanExpiry.length !== 5) {
        alert("Invalid Expiry Date");
        return;
      }

      const payload = {
        requestId: parseInt(requestId),
        cardNumber: cleanCardNumber,
        cvv: cvc,
        cardType: 1,
        cardVariant: 1,
        expiry: cleanExpiry
      };

      const response = await axios.post(
        "http://localhost:8080/api/cards/create-card",
        payload,
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`
          }
        }
      );

      console.log("Card Created:", response.data);
      alert("Card Created Successfully!");

      setName("");
      setNumber("____ ____ ____ ____");
      setExpiration("__/__");
      setCvc("");

      navigate("/admin/dashboard");

    } catch (error) {
      console.error(error);
      alert("Error creating card");
    }
  };

  return (
    <Container style={{ position: "relative" }}>
      <button onClick={handleGoBack} style={{ position: "absolute", top: "20px", left: "20px" }}>
        <IoArrowBack /> Back
      </button>

      <Card>
        <Flipper ref={flipper}>
          <CardFront>
            <Chip />
            <Name>{name}</Name>
            <Number>{number}</Number>
            <Expiration>{expiration}</Expiration>
          </CardFront>

          <CardBack>
            <BlackStripe>
              <CVC>{cvc}</CVC>
            </BlackStripe>
          </CardBack>
        </Flipper>
      </Card>

      <CardForm>
        <Input placeholder="Card Number" onChange={handleNumberChange} />
        <Input placeholder="Name" onChange={(e) => setName(e.target.value)} />

        <FormRow>
          <Input placeholder="Valid Thru (MM/YY)" onChange={handleExpiryChange} />
          <Input
            placeholder="CVC"
            onChange={(e) => setCvc(e.target.value)}
            onFocus={turnAround}
            onBlur={turnAround}
          />
        </FormRow>

        <button onClick={handleSubmit}>Create Card</button>
      </CardForm>
    </Container>
  );
}