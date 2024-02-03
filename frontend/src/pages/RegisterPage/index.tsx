import { ChangeEvent, useEffect, useRef, useState } from "react";
import * as S from "./styles";

import theme from "../../styles/theme";
import Span from "../../components/span";
import { Input } from "../../components/input";
import { useNavigate } from "react-router-dom";

import Button from "../../components/button";
import UserAPI from "../../api/userAPI";
import Utils from "../../utils/string";
import { css, styled } from "styled-components";
import AuthAPI from "../../api/authAPI";

const RegisterPage = () => {
  const emailRef = useRef<HTMLInputElement>(null);
  const passwordRef = useRef<HTMLInputElement>(null);
  const nicknameRef = useRef<HTMLInputElement>(null);

  const [warnEmail, setWarnEmail] = useState("이메일 경고 문구");
  const [warnPassword, setWarnPassword] = useState("비밀번호 경고 문구");
  const [warnNickname, setWarnNickname] = useState("닉네임 경고 문구");
  const [warnEmailCode, setWarnEmailCode] = useState("이메일 코드 경고 문구");
  const [checkedEmailCode, setCheckedEmailCode] =
    useState("이메일 코드 확인되었습니다.");

  const [hiddenEmailCodeSpan, setHiddenEmailCodeSpan] = useState(true);

  const [isValidEmail, setValidEmail] = useState(true);
  const [isValidPassword, setValidPassword] = useState(true);
  const [isValidNickname, setValidNickname] = useState(true);
  const [isValidEmailCode, setIsValidEmailCode] = useState(true);
  const [isCheckedEmailCode, setIsCheckedEmailCode] = useState(false);

  const [emailCode, setEmailCode] = useState("");
  const [emailCodeButtonActive, setEmailCodeButtonActive] = useState(false);

  const navigate = useNavigate();

  const registerHandler = async () => {
    if (
      emailRef.current === null ||
      passwordRef.current === null ||
      nicknameRef.current === null
    ) {
      return;
    }

    setValidEmail(true);
    setValidPassword(true);
    setValidNickname(true);

    const email = emailRef.current.value;
    const password = passwordRef.current.value;
    const nickname = nicknameRef.current.value;

    if (!isCheckedEmailCode) {
      setIsValidEmailCode(false);
      setHiddenEmailCodeSpan(false);
      setWarnEmailCode("이메일 코드를 인증해주세요");
      return;
    }

    if (email === "") {
      setWarnEmail("이메일을 입력해주세요.");
      setValidEmail(false);
      return;
    }

    if (Utils.isEmailFormat(email) === false) {
      setWarnEmail("이메일 형식이 올바르지 않습니다.");
      setValidEmail(false);
      return;
    }

    if (password === "") {
      setWarnPassword("비밀번호를 입력해주세요.");
      setValidPassword(false);
      return;
    }

    if (nickname === "") {
      setWarnNickname("닉네임을 입력해주세요.");
      setValidNickname(false);
      return;
    }

    const res = await UserAPI.registerUser({ email, password, nickname });

    if (res.status === 200) {
      await UserAPI.login(email, password);
      navigate("/workspace/me");
    } else if (res.status === 409) {
      setWarnEmail("이미 존재하는 이메일입니다.");
      setValidEmail(false);
    } else if (res.status === 400) {
      alert("클라이언트 오류");
    } else {
      alert("서버 오류");
    }
  };

  const emailCodeCheckHandler = async () => {
    const res = await AuthAPI.checkEmailCode(emailCode);
    if (res.status !== 200) {
      setIsValidEmailCode(false);
      setWarnEmailCode("이메일 코드가 올바르지 않습니다.");
      setHiddenEmailCodeSpan(false);
      return;
    }
    setIsValidEmailCode(true);
    setEmailCodeButtonActive(false);
    setIsCheckedEmailCode(true);
    setHiddenEmailCodeSpan(false);
  };

  const onEmailCodeChange = (e: ChangeEvent<HTMLInputElement>) => {
    setEmailCode(e.target.value);
    setIsValidEmailCode(true);
    setHiddenEmailCodeSpan(true);
    if (e.target.value.length > 0) setEmailCodeButtonActive(true);
    else setEmailCodeButtonActive(false);
  };

  const onEmailSendButtonClick = () => {
    if (emailRef.current === null) return;
    AuthAPI.sendEmailCode(emailRef.current.value);
  };

  return (
    <S.Container>
      <S.Modal>
        <S.Title>
          <S.MainTitle>계정 만들기</S.MainTitle>
        </S.Title>
        <EmailWrapper>
          <div
            style={{
              display: "flex",
              justifyContent: "space-between",
              alignItems: "center",
              padding: "0px",
              width: "100%",
            }}
          >
            <Span styles={[theme.fontFormat.subhead, theme.color.neutral]}>
              이메일
            </Span>
            <EmailInputButton onClick={onEmailSendButtonClick}>
              인증 메일 보내기
            </EmailInputButton>
          </div>

          <Input placeholder="abc@gmail.com" ref={emailRef}></Input>
          <Span
            styles={[theme.fontFormat.footnote, theme.color.systemWarning]}
            hidden={isValidEmail}
          >
            {warnEmail}
          </Span>
        </EmailWrapper>

        <EmailCheckWrapper>
          <Span styles={[theme.fontFormat.subhead, theme.color.neutral]}>
            인증 코드
          </Span>
          <Input
            placeholder="인증코드를 입력해주세요"
            onChange={onEmailCodeChange}
          ></Input>
          <Span
            styles={[
              theme.fontFormat.footnote,
              isValidEmailCode
                ? theme.color.systemDefault
                : theme.color.systemWarning,
            ]}
            hidden={hiddenEmailCodeSpan}
          >
            {isValidEmailCode ? checkedEmailCode : warnEmailCode}
          </Span>

          <InputButton
            onClick={emailCodeCheckHandler}
            active={emailCodeButtonActive}
          >
            인증
          </InputButton>
        </EmailCheckWrapper>

        <S.InputWrapper>
          <Span styles={[theme.fontFormat.subhead, theme.color.neutral]}>
            비밀번호
          </Span>
          <Input placeholder="12345678" ref={passwordRef} type="password"></Input>
          <Span
            styles={[theme.fontFormat.footnote, theme.color.systemWarning]}
            hidden={isValidPassword}
          >
            {warnPassword}
          </Span>
        </S.InputWrapper>

        <S.InputWrapper>
          <Span styles={[theme.fontFormat.subhead, theme.color.neutral]}>
            닉네임
          </Span>
          <Input placeholder="your nickname" ref={nicknameRef}></Input>
          <Span
            styles={[theme.fontFormat.footnote, theme.color.systemWarning]}
            hidden={isValidNickname}
          >
            {warnNickname}
          </Span>
        </S.InputWrapper>

        <S.ButtonWrapper>
          <Button
            styles={[theme.color.backgroundSoftBlue, theme.color.neutral]}
            onClick={() => {
              registerHandler();
            }}
          >
            회원 가입
          </Button>
        </S.ButtonWrapper>
      </S.Modal>
    </S.Container>
  );
};

export default RegisterPage;

const InputButton = styled(Button)<{ active: boolean }>`
  ${({ theme }) => theme.color.backgroundSoftBlue}
  ${({ theme }) => theme.color.neutral}
  height: 25px;

  width: fit-content;
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  right: 35px;
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

export const EmailCheckWrapper = styled.div`
  position: relative;
  padding: 0px 30px;
  ${({ theme }) => theme.flex.columnCenterStart}
  align-self: stretch;
  gap: 5px;
`;

export const EmailWrapper = styled.div`
  position: relative;
  padding: 0px 30px;
  ${({ theme }) => theme.flex.columnCenterStart}
  align-self: stretch;
`;

export const EmailInputButton = styled(Button)`
  ${({ theme }) => theme.color.backgroundSoftBlue}
  ${({ theme }) => theme.color.neutral}
  height: 25px;

  width: fit-content;
  margin: 0px;
  margin-bottom: 5px;

  &:active {
    background-color: #2e3136;
  }
`;
