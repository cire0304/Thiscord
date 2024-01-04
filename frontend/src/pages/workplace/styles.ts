import { styled } from "styled-components";

export const Container = styled.div`
  width: 100vw;
  height: 100vh;

  position: relative;
  ${({ theme }) => theme.flex.columnStartCenter}
`;

export const Header = styled.header`
  width: 100%;
  height: 50px;
  ${({ theme }) => theme.flex.rowStartCenter}
  border-bottom: 1px solid ${({ theme }) => theme.color.border};
`;

export const Body = styled.body`
  width: 100%;
  flex-grow: 1;
  ${({ theme }) => theme.flex.rowStartCenter}
`;
