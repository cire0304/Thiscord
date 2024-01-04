import { styled } from "styled-components";

export const Container = styled.div`
  height: 100%;

  padding: 10px;
  ${({ theme }) => theme.color.backgroundTertiary}
  ${({ theme }) => theme.flex.rowStartCenter}
    flex-grow: 1;
`;

export const Text = styled.span`
  color: white;
  margin: 0px 5px;
  padding: 0px 10px;
  border-right: 1px solid white;
  
  -webkit-touch-callout: none;
  -webkit-user-select: none;
  -khtml-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
`;

export const Navigates = styled.div`
  ${({ theme }) => theme.flex.rowStartCenter}
`;

export const Button = styled.button<{ active?: boolean }>`
  ${({ theme }) => theme.color.neutral}
  ${({ theme }) => theme.flex.rowStartCenter}
  ${({ theme }) => theme.color.backgroundTertiary}

  padding: 3px 10px;
  border: none;
  border-radius: 5px;

  cursor: pointer;
  transition: 0.2s;

  &:hover {
    background-color: #3e4249;
  }

  &:active {
    background-color: #2e3136;
  }

  ${({ active }) =>
    active &&
    `
        background-color: #3e4249;
    `}
`;

export const FriendButton = styled.button`
  ${({ theme }) => theme.color.neutral}
  ${({ theme }) => theme.flex.rowStartCenter}
  background-color: #248046;
  margin-left: 5px;
  padding: 3px 10px;
  border: none;
  border-radius: 5px;

  cursor: pointer;
  transition: 0.2s;

  &:hover {
    background-color: #349056;
  }

  &:active {
    background-color: #2e3136;
  }

`;
