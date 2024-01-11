import { ReactNode } from "react";
import styled, { RuleSet, css } from "styled-components";

const Span = styled.span<SpanElementProps>`
  display: inline-block;

  font-weight: 900px;

  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;

  ${({ styles }) => styles}
  ${({ hidden }) =>
    hidden &&
    css`
      visibility: hidden;
    `}
`;

interface SpanElementProps extends React.ComponentProps<"span"> {
  children?: ReactNode;
  styles?: RuleSet<object>;
  hidden?: boolean;
}

export default Span;
