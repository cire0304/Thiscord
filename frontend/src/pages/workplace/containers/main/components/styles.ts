import { styled } from "styled-components";
import Span from "../../../../../components/span";

export const Container = styled.div`
  padding: 20px 20px;
`;

export const Header = styled.div`
  padding-bottom: 15px;
  border-bottom: 1px solid #404147;
  display: flex;
  justify-content: flex-start;
`;
export const Title = styled(Span)`
  font-size: 12px;
  font-weight: 700;

  ${({ theme }) => theme.color.primary}
  ${({ theme }) => theme.dragable.none}
`;

export const Body = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
`;

export const Friend = styled.div`
  padding: 4px 0px;
  width: 100%;
  display: flex;
  align-items: center;
  gap: 10px;

  border-bottom: 1px solid #404147;
`;

export const Info = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
  gap: 5px;
  flex-grow: 1;
`;

export const Nickname = styled(Span)`
  font-size: 16px;
  font-weight: 700;

  ${({ theme }) => theme.color.neutral}
`;

export const Type = styled(Span)`
  font-size: 12px;
  font-weight: 700;
  ${({ theme }) => theme.color.primary}
`;

export const Button = styled.button`
  width: 30px;
  height: 30px;
  border-radius: 100%;
  border: none;
  opacity: 0.5;
  background-color: #fff;

  transition: 0.3s;

  &:hover {
    opacity: 1;
  }

  &:active {
    opacity: 0.5;
  }
`;