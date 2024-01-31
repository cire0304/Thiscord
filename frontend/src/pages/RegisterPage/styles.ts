import styled from "styled-components";

export const Container = styled.div`
  width: 100vw;
  height: 100vh;

  position: relative;
  background: url("background.png");
`;

export const Modal = styled.div`
  width: 343px;
  padding-bottom: 20px;

  border-radius: 5px;
  ${({ theme }) => theme.color.backgroundPrimary}
  ${({ theme }) => theme.position.center}
  ${({ theme }) => theme.flex.columnStartCenter}

  animation: fadein 0.5s;
  @keyframes fadein {
    from {
      opacity: 0;
    }
    to {
      opacity: 1;
    }
  }
`;

export const Title = styled.div`
  padding: 30px 27px 15px 27px;
  gap: 10px;
  ${({ theme }) => theme.flex.columnCenterCenter}
  align-self: stretch;
`;

export const MainTitle = styled.span`
  ${({ theme }) => theme.fontFormat.title2}
  ${({ theme }) => theme.color.neutral}
`;

export const InputWrapper = styled.div`
  padding: 0px 30px;
  ${({ theme }) => theme.flex.columnCenterStart}
  align-self: stretch;
  gap: 5px;
`;

export const ButtonWrapper = styled.div`
  padding: 0px 30px 5px 30px;

  ${({ theme }) => theme.flex.columnCenterCenter}
  align-self: stretch;
`;

export const FooterWrapper = styled.div`
  padding: 20px 30px 5px 30px;

  ${({ theme }) => theme.flex.columnCenterCenter}
  align-self: stretch;
`;
