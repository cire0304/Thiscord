import React, { useRef, useState } from "react";
import { css, styled } from "styled-components";
import Span from "../../../components/span";

import { Input } from "../../../components/input";
import Button from "../../../components/button";
import FriendReqeust from "../../../api/friend";

const Container = styled.div`
  padding: 20px 20px;
  border-bottom: 1px solid #404147;
`;

const Header = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
`;

const Body = styled.div`
  margin-top: 10px;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;

  position: relative;
`;

const Title = styled.span`
  font-size: 16px;
  font-weight: 700;
  ${({ theme }) => theme.color.neutral}
  ${({ theme }) => theme.dragable.none}
`;
const SubTitle = styled.span`
  font-size: 12px;
  ${({ theme }) => theme.color.primary}
  ${({ theme }) => theme.dragable.none}
`;

const WarningMessage = styled(Span)`
  margin-top: 5px;
  font-size: 12px;
  ${({ theme }) => theme.color.systemWarning}
`;

const InputWrapper = styled.div`
  position: relative;
  width: 100%;
  height: 40px;

  display: flex;
  align-items: center;
`;

const InputButton = styled(Button)<{ active: boolean }>`
  ${({ theme }) => theme.color.backgroundSoftBlue}
  ${({ theme }) => theme.color.neutral}
  height: 25px;
  width: 125px;
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  right: 10px;
  margin: 0;

  opacity: 0.5;
  pointer-events: none;
  ${({ active }) =>
    active &&
    css`
      opacity: 1;
      pointer-events: auto;
    `}

  &:active {
    background-color: #2e3136;
  }
`;

const AddFriend = () => {
  const [buttonActive, setButtonActive] = useState(false);
  const [inputValue, setInputValue] = useState("");
  const [hidden, setHidden] = useState(true);
  const [warningMessage, setWarningMessage] = useState("");

  const nicknameAnduserCodeRef = useRef<HTMLInputElement>(null);

  const inputChangeHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    e.preventDefault();
    const value = e.target.value;
    setInputValue(value);
    if (value.length > 0) setButtonActive(true);
    else setButtonActive(false);
  };

  const buttonClickHandler = () => {
    const nickname = inputValue.split("#")[0];
    const userCode = inputValue.split("#")[1];
    console.log(nickname, userCode);
    if (
      nickname === undefined ||
      nickname.length < 1 ||
      userCode === undefined ||
      userCode.length < 1
    ) {
      setWarningMessage(
        "형식이 잘못된거 같아요. 올바르게 입력했는지 확인해보세요."
      );
      setHidden(false);
      return;
    }

    const addFreindRequest = async () => {
      const data = { nickname, userCode };
      const res = await FriendReqeust.addFriend(data);

      if (res.status !== 200) {
        setWarningMessage(res.data);
        setHidden(false);
      }
    };
    addFreindRequest();
  };

  return (
    <Container>
      <Header>
        <Title>친구 추가하기</Title>
        <SubTitle>
          시레#123456 와 같이 사용자명을 사용하여 친구를 추가할 수 있어요
        </SubTitle>
      </Header>
      <Body>
        <InputWrapper>
          <Input
            onChange={(e) => inputChangeHandler(e)}
            ref={nicknameAnduserCodeRef}
          />
          <InputButton
            onClick={() => buttonClickHandler()}
            active={buttonActive}
          >
            친구 요청 보내기
          </InputButton>
        </InputWrapper>
        <WarningMessage hidden={hidden}>{warningMessage}</WarningMessage>
      </Body>
    </Container>
  );
};

export default AddFriend;
