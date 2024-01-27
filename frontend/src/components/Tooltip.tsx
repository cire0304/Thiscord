import React from "react";
import { styled } from "styled-components";
import Span from "./span";

export default function Tooltip({
  children,
  message,
}: {
  children: React.ReactNode;
  message: string;
}) {
  return (
    <Container>
      {children}
      <TooltipBox>{message}</TooltipBox>
    </Container>
  );
}

const TooltipBox = styled(Span)`
  display: inline-block;
  background-color: black;
  color: #fff;
  text-align: center;
  border-radius: 6px;
  padding: 5px 10px;
  width: 120px;

  position: absolute;
  top: 120%;
  left: 50%;
  transform: translateX(-50%);
  z-index: 1;
  
  opacity: 0;
  transition: all 0.2s ease-in-out;
`;

const Container = styled.div`
  position: relative;
  display: inline-block;

  &:hover ${TooltipBox} {
    opacity: 1;
  }
  & ${TooltipBox}::after {
    content: " ";
    position: absolute;
    bottom: 100%;
    left: 50%;

    margin-left: -5px;
    border-width: 5px;
    border-style: solid;
    border-color: transparent transparent black transparent;
  }
`;
