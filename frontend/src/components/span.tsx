import { ReactNode } from "react";
import styled, { RuleSet, css } from "styled-components";

const Span = styled.span<SpanElementProps>`
  display:inline-block;
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
