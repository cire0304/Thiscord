import React, { useRef, Dispatch, SetStateAction } from "react";
import { styled } from "styled-components";
import Span from "../../../../components/span";
import theme from "../../../../styles/theme";

const Modal = styled.div`
  width: 100vw;
  height: 100vh;

  position: absolute;
  top: 0;
  left: 0;

  background-color: rgba(0, 0, 0, 0.4);
`;

const Container = styled.div`
  height: 500px;
  width: 500px;

  border-radius: 10px;

  position: relative;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);

  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items : center;
  background-color: #242428;

  animation: fadein 0.3s;
  @keyframes fadein {
    from {
      opacity: 0;
      transform: translate(-50%, -50%) scale(0.8);
    }
    to {
      opacity: 1;
      transform: translate(-50%, -50%) scale(1);
    }
  }
  
`;

const Header = styled.div`
  height: 70px;
  width: 100%;

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items : center;

  ${({ theme }) => theme.color.backgroundTertiary};
`;

const Body = styled.div`
  padding: 20px;
  width: 100%;
  height: 100%;
`;

const Wraper = styled.div`
  width: 100%;
  height: 100%;
  padding: 20px;

  flex-direction: column;
  justify-content: center;
  align-items : flex-start;
  gap: 10px;

  border-radius: 10px;
  ${({ theme }) => theme.color.backgroundSecondary};
`;

const Content = styled.div`
  width: 100%;

  padding: 10px;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items : flex-start;
  border-radius: 10px;
  
  border-bottom: 1px solid #3a3a3d;
  ${({ theme }) => theme.color.backgroundSecondary};
  text-align: left;
` 

const Introduction = styled(Content)`
  border-bottom: none;
`;

const ProfileModal = ({setIsProfileModalActive} : {setIsProfileModalActive: Dispatch<SetStateAction<boolean>>}) => {

  const backgroundRef = useRef<HTMLDivElement>(null);
  const onCancle = (e: React.MouseEvent<HTMLDivElement>) => {
    if (backgroundRef.current === e.target) {
      setIsProfileModalActive(false);
  };

  };

  return (
    <Modal onClick={(e) => onCancle(e)} ref={backgroundRef}>
      <Container>
        <Header>
          <Span styles={[theme.fontFormat.title1, theme.color.neutral]}>
            나의 프로필
          </Span>
        </Header>

        <Body>
          <Wraper>
            <Content>
              <Span styles={[theme.fontFormat.headline, theme.color.neutral]}>닉네임</Span>
              <Span styles={[theme.fontFormat.title3, theme.color.neutral]}>시레</Span>
            </Content>
            <Content>
              <Span styles={[theme.fontFormat.headline, theme.color.neutral]}>초대코드</Span>
              <Span styles={[theme.fontFormat.title3, theme.color.neutral]}>시레#1234</Span>
            </Content>
            <Content>
              <Span styles={[theme.fontFormat.headline, theme.color.neutral]}>이메일</Span>
              <Span styles={[theme.fontFormat.title3, theme.color.neutral]}>dltpwns0@gmail.com</Span>
            </Content>
            <Introduction>
              <Span styles={[theme.fontFormat.headline, theme.color.neutral]}>자기소개</Span>
              <Span styles={[theme.fontFormat.title3, theme.color.neutral]}>프론트 쉽지 않네프론트 쉽지 않네프론트 쉽지 않네프론트 쉽지 않네프론트 쉽지 않네프론트 쉽지 않네프론트 프론트 쉽지 않네</Span>
            </Introduction>
          </Wraper>
        </Body>
      </Container>
    </Modal>
  );
};

export default ProfileModal;
