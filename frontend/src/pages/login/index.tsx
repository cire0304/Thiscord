import React, { useRef, useState } from "react";
import * as S from "./styles";
import { ThemeProvider } from "styled-components";
import theme from "../../styles/theme";
import Span from "../../components/span";
import { Input } from "../../components/input";
import { useNavigate } from "react-router-dom";
import Button from "../../components/button";
import { GOOGLE_LOGIN_URL } from "../../constants/constants";

const LoginPage = () => {
  const emailRef = useRef(null);
  const navigate = useNavigate();

  const [isValidEmail, setValidEmail] = useState(true);
  const [isValidPassword, setValidPassword] = useState(true);

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
            visable={isValidEmail}
          >
            잘못된 이메일 형식입니다.
          </Span>
        </S.InputWrapper>
        <S.InputWrapper>
          <Span styles={[theme.fontFormat.subhead, theme.color.neutral]}>
            비밀번호
          </Span>
          <Input placeholder="abc@gmail.com" ref={emailRef}></Input>

          <Span
            styles={[theme.fontFormat.footnote, theme.color.systemWarning]}
            visable={isValidPassword}
          >
            잘못된 비밀번호 형식입니다.
          </Span>
        </S.InputWrapper>

        <S.ButtonWrapper>
          <Button styles={[theme.color.backgroundSoftBlue, theme.color.neutral]} onClick={() => {}}>로그인</Button>
          <Button onClick={() => {window.location.href = GOOGLE_LOGIN_URL;}}>Google로 로그인 </Button>
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
