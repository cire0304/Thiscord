import React, { useRef, useState } from "react";
import * as S from "./styles";
import { ThemeProvider } from "styled-components";
import theme from "../../styles/theme";
import Span from "../../components/span";
import { Input } from "../../components/input";
import { useNavigate } from "react-router-dom";
import Button from "../../components/button";
import { GOOGLE_LOGIN_URL } from "../../constants/constants";
import Utils from "../../utils/string";
import UserRequest from "../../api/user";

const LoginPage = () => {
  const emailRef = useRef<HTMLInputElement>(null);
  const passwordRef = useRef<HTMLInputElement>(null);

  const [warnEmail, setWarnEmail] = useState("이메일 경고 문구");
  const [warnPassword, setWarnPassword] = useState("비밀번호 경고 문구");

  const [isValidEmail, setValidEmail] = useState(true);
  const [isValidPassword, setValidPassword] = useState(true);

  const navigate = useNavigate();

  const loginHandler = async () => {
    if (emailRef.current === null || passwordRef.current === null) {
      return;
    }

    setValidEmail(true);
    setValidPassword(true);

    const email = emailRef.current.value;
    const password = passwordRef.current.value;

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

    const res = await UserRequest.login(email, password);
    if (res.status === 200) {
      navigate("/workspace");
    } else {
      setWarnPassword("이메일이 존재하지 않거나 비밀번호가 틀렸습니다.");
      setValidPassword(false);
    }
  };

  return (
    <S.Container>
      <S.Modal>
        <S.Title>
          <S.MainTitle>Thiscord에 오신걸 환영해요!</S.MainTitle>
          <S.SubTitle>열심히 만들었어요 (˙ ˘ ˙)</S.SubTitle>
        </S.Title>

        <S.InputWrapper>
          <Span styles={[theme.fontFormat.subhead, theme.color.neutral]}>
            이메일
          </Span>
          <Input placeholder="abc@gmail.com" ref={emailRef}></Input>
          <Span
            styles={[theme.fontFormat.footnote, theme.color.systemWarning]}
            hidden={isValidEmail}
            // hidden={true}

          >
            {warnEmail}
          </Span>

        </S.InputWrapper>
        <S.InputWrapper>
          <Span styles={[theme.fontFormat.subhead, theme.color.neutral]}>
            비밀번호
          </Span>
          <Input placeholder="12345678" ref={passwordRef}></Input>

          <Span
            styles={[theme.fontFormat.footnote, theme.color.systemWarning]}
            hidden={isValidPassword}

          >
            {warnPassword}
          </Span>
        </S.InputWrapper>

        <S.ButtonWrapper>
          <Button
            styles={[theme.color.backgroundSoftBlue, theme.color.neutral]}
            onClick={() => {
              loginHandler();
            }}
          >
            로그인
          </Button>
          <Button
            onClick={() => {
              window.location.href = GOOGLE_LOGIN_URL;
            }}
          >
            Google로 로그인{" "}
          </Button>
        </S.ButtonWrapper>

        <S.FooterWrapper>
          <Span styles={[theme.fontFormat.footnote, theme.color.neutral]}>
            계정이 없다면{" "}
            <Span
              styles={[theme.fontFormat.footnote, theme.color.neutralBlue]}
              onClick={() => {
                navigate("/register");
              }}
            >
              회원가입
            </Span>
            을 해보는게 어떠세요?
          </Span>
        </S.FooterWrapper>
      </S.Modal>
    </S.Container>
  );
};

export default LoginPage;
