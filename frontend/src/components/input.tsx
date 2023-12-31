import styled from "styled-components";

export const Input = styled.input`
  height: 32px;
  width: 100%;

  padding: 5px 10px;
  ${({ theme }) => theme.color.backgroundSecondary}
  ${({ theme }) => theme.color.neutral}
    flex-grow: 1;

  border: none;
`;
