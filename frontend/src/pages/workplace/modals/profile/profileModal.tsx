import React, { useRef, Dispatch, SetStateAction, useEffect, useState } from "react";
import { styled } from "styled-components";
import Span from "../../../../components/span";
import theme from "../../../../styles/theme";
import UserRequest from "../../../../api/user";
import Utils from "../../../../utils/string";

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
  align-items: center;
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
  align-items: center;

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
  align-items: flex-start;
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
  align-items: flex-start;
  border-radius: 10px;

  border-bottom: 1px solid #3a3a3d;
  ${({ theme }) => theme.color.backgroundSecondary};
  text-align: left;
`;

const Introduction = styled(Content)`
  border-bottom: none;
  align-items: stretch;
  height: 150px;
`;


const Input = styled.input`
  ${({ theme }) => theme.fontFormat.headline};
  ${({ theme }) => theme.color.neutral};
  ${({ theme }) => theme.color.backgroundPrimary};
  border: none;
  border-radius: 5px;
  width: 100%;
`;

const Textarea = styled.textarea`
  ${({ theme }) => theme.fontFormat.headline};
  ${({ theme }) => theme.color.neutral};
  ${({ theme }) => theme.color.backgroundPrimary};
  height: 100%;
  border: none;
  border-radius: 5px;
  resize: none;
`;

interface ProfileModalProps {
  isProfileModalActive: boolean;
  setIsProfileModalActive: Dispatch<SetStateAction<boolean>>;
}

interface UserDetailInfo {
  nickname: string;
  email: string;
  introduction: string;
  createdAt: string;
  userCode: string;
}

const ProfileModal = ({
  isProfileModalActive,
  setIsProfileModalActive,
}: ProfileModalProps) => {
  const loding = "로딩중...";
  const backgroundRef = useRef<HTMLDivElement>(null);
  const [userDetailInfo, setUserDetailInfo] = useState<UserDetailInfo>({
    nickname: "",
    email: "",
    introduction: "",
    createdAt: "",
    userCode: "",
  });

  const nicknameInputRef = useRef<HTMLInputElement>(null);
  const intoductionTextAreaRef = useRef<HTMLTextAreaElement>(null);

  const onCancle = (e: React.MouseEvent<HTMLDivElement>) => {
    if (backgroundRef.current === e.target) {
      setIsProfileModalActive(false);
    }
  };

  useEffect(() => {
    const getUserDetailInfo = async () => {
      const res = await UserRequest.getUserDetailInfo();
      setUserDetailInfo(res.data);
    };
    if (isProfileModalActive) {
      getUserDetailInfo();
    }
  }, [isProfileModalActive]);

  const updateUserDetailInfo = async () => {
    if (
      nicknameInputRef.current === null ||
      intoductionTextAreaRef.current === null
    )
      return;
    const nickname = nicknameInputRef.current.value;
    const introduction = intoductionTextAreaRef.current.value;
    const res = await UserRequest.updateUserInfo(
      nickname,
      introduction,
    );
    setUserDetailInfo({...userDetailInfo, nickname, introduction });
  };

  const handlerChangeUserDetailInfo = (
    e: React.FocusEvent<HTMLInputElement> | React.FocusEvent<HTMLTextAreaElement>
  ) => {
    if (e.target === null) return;
    if (e.target.value !== e.target.defaultValue) {
      updateUserDetailInfo();
    }
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
              <Span styles={[theme.fontFormat.headline, theme.color.neutral]}>
                닉네임
              </Span>
              {Utils.isEmpty(userDetailInfo.nickname) ? (
                loding
              ) : (
                <Input
                  type="text"
                  onBlur={(e) => {
                    handlerChangeUserDetailInfo(e);
                  }}
                  defaultValue={userDetailInfo.nickname}
                  ref={nicknameInputRef}
                />
              )}
            </Content>
            <Content>
              <Span styles={[theme.fontFormat.headline, theme.color.neutral]}>
                초대코드
              </Span>
              <Span styles={[theme.fontFormat.title3, theme.color.neutral]}>
                {Utils.isEmpty(userDetailInfo.userCode)
                  ? loding
                  : userDetailInfo.userCode}
              </Span>
            </Content>
            <Content>
              <Span styles={[theme.fontFormat.headline, theme.color.neutral]}>
                이메일
              </Span>
              <Span styles={[theme.fontFormat.title3, theme.color.neutral]}>
                {Utils.isEmpty(userDetailInfo.email)
                  ? loding
                  : userDetailInfo.email}
              </Span>
            </Content>
            <Introduction>
              <Span styles={[theme.fontFormat.headline, theme.color.neutral]}>
                자기소개
              </Span>
              {Utils.isEmpty(userDetailInfo.introduction) ? (
                loding
              ) : (
                <Textarea
                  onBlur={(e) => {
                    handlerChangeUserDetailInfo(e);
                  }}
                  defaultValue={userDetailInfo.introduction}
                  ref={intoductionTextAreaRef}
                />
              )}

              {/* <Span styles={[theme.fontFormat.title3, theme.color.neutral]}>
                {userDetailInfo.introduction}
              </Span> */}
            </Introduction>
          </Wraper>
        </Body>
      </Container>
    </Modal>
  );
};

export default ProfileModal;
