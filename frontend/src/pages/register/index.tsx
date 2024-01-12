import { useRef, useState } from "react";
import * as S from "./styles";

import theme from "../../styles/theme";
import Span from "../../components/span";
import { Input } from "../../components/input";
import { useNavigate } from "react-router-dom";

import Button from "../../components/button";
import UserAPI from "../../api/userAPI";
import Utils from "../../utils/string";

const RegisterPage = () => {
  const emailRef = useRef<HTMLInputElement>(null);
  const passwordRef = useRef<HTMLInputElement>(null);
  const nicknameRef = useRef<HTMLInputElement>(null);

  const [warnEmail, setWarnEmail] = useState("이메일 경고 문구");
  const [warnPassword, setWarnPassword] = useState("비밀번호 경고 문구");
  const [warnNickname, setWarnNickname] = useState("닉네임 경고 문구");

  const [isValidEmail, setValidEmail] = useState(true);
  const [isValidPassword, setValidPassword] = useState(true);
  const [isValidNickname, setValidNickname] = useState(true);

  const navigate = useNavigate();

  const isEmailFormat = (email: string) => {
    const emailRegex = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
    return emailRegex.test(email);
  };

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
      navigate("/login");
    } else if (res.status === 409) {
      setWarnEmail("이미 존재하는 이메일입니다.");
      setValidEmail(false);
    } else if (res.status === 400) {
      alert("클라이언트 오류");
    } else {
      alert("서버 오류");
    }
  };

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
            hidden={isValidEmail}
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
