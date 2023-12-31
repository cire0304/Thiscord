import { ReactNode } from "react";
import styled, { RuleSet, css } from "styled-components";

const Span = styled.span<SpanElementProps>`
  ${({ styles }) => styles}
  ${({ visable }) =>
    visable &&
    css`
      visibility: hidden;
    `}
`;

interface SpanElementProps extends React.ComponentProps<"span"> {
  children?: ReactNode;
  styles?: RuleSet<object>;
  visable?: boolean;
}

export default Span;
