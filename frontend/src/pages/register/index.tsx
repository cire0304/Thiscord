import React, { useRef, useState } from "react";
import * as S from "./styles";
import { ThemeProvider } from "styled-components";
import theme from "../../styles/theme";
import Span from "../../components/span";
import { Input } from "../../components/input";
import { useNavigate } from "react-router-dom";

import { GOOGLE_LOGIN_URL } from "../../constants/constants";
import Button from "../../components/button";

const RegistgerPage = () => {
  const emailRef = useRef(null);
  const navigate = useNavigate();

  const [isValidEmail, setValidEmail] = useState(true);
  const [isValidPassword, setValidPassword] = useState(true);

  return (
    <S.Container>
      <S.Modal>
        <S.Title>
          <S.MainTitle>계정 만들기</S.MainTitle>
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
          <Input placeholder="12345678" ref={emailRef}></Input>
          <Span
            styles={[theme.fontFormat.footnote, theme.color.systemWarning]}
            visable={isValidEmail}
          >
            패스워드는 8자 이상이어야 합니다. 
          </Span>
        </S.InputWrapper>

        <S.InputWrapper>
          <Span styles={[theme.fontFormat.subhead, theme.color.neutral]}>
            닉네임
          </Span>
          <Input placeholder="your nickname" ref={emailRef}></Input>
          <Span
            styles={[theme.fontFormat.footnote, theme.color.systemWarning]}
            visable={isValidEmail}
          >
            닉네임은 공백일 수 없어요
          </Span>
        </S.InputWrapper>

        <S.ButtonWrapper>
          <Button styles={[theme.color.backgroundSoftBlue, theme.color.neutral]} onClick={() => {}}>로그인</Button>
        </S.ButtonWrapper>
     
      </S.Modal>
    </S.Container>
  );
};

export default RegistgerPage;
