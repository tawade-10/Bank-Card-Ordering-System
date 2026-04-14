import React, { useRef, useState, useCallback } from "react";
import { Card, Container, Flipper, CardFront, CardBack, CardForm, Input, FormRow, Name, Number, Expiration, Chip, BlackStripe, CVC } from "./style";

export default function CardCreation() {
  const flipper = useRef(null);
  const [side, setSide] = useState("front");
  const [name, setName] = useState('')
  const [number, setNumber] = useState('____ ____ ____ ____')
  const [expiration, setExpiration] = useState('__/__')
  const [cvc, setCvc] = useState('');

  const turnAround = useCallback(() => {
    if (!flipper.current) return;

    const isFront = side === "front";

    flipper.current.style.transform = isFront
      ? "rotateY(180deg)"
      : "rotateY(0deg)";

    setSide(prev => (prev === "front" ? "back" : "front"));
  }, [side]);

  return (
    <Container>
      <Card>
        <Flipper ref={flipper}>
          <CardFront>
              <Chip>
                  </Chip>
              <Name>
                  {name}
              </Name>
              <Number>
                  {number}
              </Number>
              <Expiration>
                  {expiration}
              </Expiration>
          </CardFront>
          <CardBack>
              <BlackStripe>
                  <CVC>
                      {cvc}
                  </CVC>
              </BlackStripe>
          </CardBack>
        </Flipper>
      </Card>
      <CardForm>
          <Input placeholder="Card Number" onChange={e => setNumber(e.target.value)}/>
          <Input placeholder="Name" onChange={e => setName(e.target.value)}/>
          <FormRow>
              <Input placeholder="Valid Thru" onChange={e => setExpiration(e.target.value)}/>
              <Input placeholder="CVC" onChange={e => setCvc(e.target.value)} onFocus={turnAround} onBlur={turnAround}/>
          </FormRow>
      </CardForm>
    </Container>
  );
}