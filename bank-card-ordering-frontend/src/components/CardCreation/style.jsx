import styled, { css } from "styled-components";

export const Container = styled.div`
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

export const Card = styled.div`
  height: 200px;
  width: 300px;
  perspective: 1000px;
  margin-bottom: 10px;
`;

export const Flipper = styled.div`
  position: relative;
  height: 100%;
  width: 100%;
  transition: transform 0.8s;
  transform-style: preserve-3d;
`;

const sideStyle = css`
  position: absolute;
  inset: 0;
  backface-visibility: hidden;
  border-radius: 12px;
  background: linear-gradient(135deg, #6290e4, #2053b1);
  display: flex;
  justify-content: center;
  align-items: center;
  color: white;
  font-size: 18px;
`;

export const CardFront = styled.div`
  ${sideStyle};
`;

export const CardBack = styled.div`
  ${sideStyle};
  transform: rotateY(180deg);
`;

export const CardForm = styled.div`
  display: flex;
  flex-direction: column;
  width: 300px;
`

export const Input = styled.input`
  margin-bottom: 10px;
  border: 1px solid #ddd;
  height: 20px;
  border-radius: 5px;
  padding: 10px;

  &::placeholder{
      color: #aaa;
  }
`

export const FormRow = styled.div`
  display: flex;
  width: 100%;
  gap: 10px;

  & input:fist-child{
      width: 70%;
  }

  & input:nth-child(2){
      width: 30%;
  }
`

export const Name = styled.span`
  font-size: 16px;
  font-weight: bold;
  position: absolute;
  bottom: 20px;
  left: 20px;
  text-transform: uppercase;
`

export const Number = styled.span`
  font-size: 16px;
  font-weight: bold;
  position: absolute;
  bottom: 40px;
  left: 20px;
`

export const Expiration = styled.span`
  font-size: 16px;
  font-weight: bold;
  position: absolute;
  bottom: 20px;
  right: 20px;
`

export const Chip = styled.div`
  position: absolute;
  top: 20px;
  left: 20px;
  width: 60px;
  height: 40px;
  border-radius: 12px;
  background: #aaa;
`

export const BlackStripe = styled.div`
  position: absolute;
  height: 40px;
  top: 50px;
  left: 0;
  right: 0;
  background: #000;
`

export const CVC = styled.div`
  position: absolute;
  top: 90px;
  right: 20px;
  height: 40px;
  width: 60px;
  border-radius: 12px;
  background: #fff;
  font-size: 14px;
  color: #333;
  display: flex;
  align-items: center;
  justify-content: center;
  font-style: italic;
`