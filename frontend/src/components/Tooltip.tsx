import React from "react";
import { styled } from "styled-components";

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

const TooltipBox = styled.div`
  visibility: hidden;
  background-color: black;
  color: #fff;
  text-align: center;
  border-radius: 6px;
  padding: 5px 10px;

  position: absolute;
  z-index: 1;
`;

const Container = styled.div`
  position: relative;
  width: fit-content;
  height: fit-content;
  display: inline-block;
  font-size: 1rem;

  &:hover ${TooltipBox} {
    visibility: visible;
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
