import React, {
  useRef,
  Dispatch,
  SetStateAction,
} from "react";
import { useDispatch, useSelector } from "react-redux";
import { styled } from "styled-components";
import UserAPI, { UserInfo } from "../../../api/user";
import { setUserInfoState } from "../../../store";
import Span from "../../../components/span";
import theme from "../../../styles/theme";

interface ProfileModalProps {
  isProfileModalActive: boolean;
  setIsProfileModalActive: Dispatch<SetStateAction<boolean>>;
}


export default function ProfileModal({
  isProfileModalActive,
  setIsProfileModalActive,
}: ProfileModalProps)  {

  const backgroundRef = useRef<HTMLDivElement>(null);

  const user = useSelector((state: any) => state.user) as UserInfo;
  const dispatch = useDispatch();

  const nicknameInputRef = useRef<HTMLInputElement>(null);
  const intoductionTextAreaRef = useRef<HTMLTextAreaElement>(null);

  const onCancle = (e: React.MouseEvent<HTMLDivElement>) => {
    if (backgroundRef.current === e.target) {
      setIsProfileModalActive(false);
    }
  };


  const updateUserInfo = async () => {
    if (
      nicknameInputRef.current === null ||
      intoductionTextAreaRef.current === null
    )
      return;
    const nickname = nicknameInputRef.current.value;
    const introduction = intoductionTextAreaRef.current.value;
    await UserAPI.updateUserInfo(nickname, introduction);
    const res = await UserAPI.getUserInfo();
    dispatch(setUserInfoState(res.data));
  };

  const handlerChangeUserInfo = (
    e:
      | React.FocusEvent<HTMLInputElement>
      | React.FocusEvent<HTMLTextAreaElement>
  ) => {
    if (e.target === null) return;
    if (e.target.value !== e.target.defaultValue) {
      updateUserInfo();
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

              <Input
                type="text"
                onBlur={(e) => {
                  handlerChangeUserInfo(e);
                }}
                defaultValue={user.nickname}
                ref={nicknameInputRef}
              />
            </Content>
            <Content>
              <Span styles={[theme.fontFormat.headline, theme.color.neutral]}>
                초대코드
              </Span>
              <Span styles={[theme.fontFormat.title3, theme.color.neutral]}>
                {user.userCode}
              </Span>
            </Content>
            <Content>
              <Span styles={[theme.fontFormat.headline, theme.color.neutral]}>
                이메일
              </Span>
              <Span styles={[theme.fontFormat.title3, theme.color.neutral]}>
                {user.email}
              </Span>
            </Content>
            <Introduction>
              <Span styles={[theme.fontFormat.headline, theme.color.neutral]}>
                자기소개
              </Span>
              <Textarea
                onBlur={(e) => {
                  handlerChangeUserInfo(e);
                }}
                defaultValue={user.introduction}
                ref={intoductionTextAreaRef}
              />
            </Introduction>
          </Wraper>
        </Body>
      </Container>
    </Modal>
  );
};

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

