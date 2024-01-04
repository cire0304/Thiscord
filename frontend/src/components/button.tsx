import styled, { RuleSet } from "styled-components";

const ButtonElement = styled.button<ButtonElementProps>`
  width: 100%;
  padding: 8px 16px;
  margin: 5px 10px;
  border: none;
  border-radius: 5px;
  ${({ theme }) => theme.flex.columnCenterCenter}
  ${({ styles }) => styles}
  
  cursor: pointer;
  transition: 0.2s;
  
  &:hover{
    ${({ theme }) => theme.color.primary}
  }
`;

interface ButtonElementProps extends React.ComponentProps<"button"> {
  styles?: RuleSet<object>;
}

const Button = ({ children, ...props }: ButtonElementProps) => {
  return <ButtonElement {...props}>{children}</ButtonElement>;
};

export default Button;
