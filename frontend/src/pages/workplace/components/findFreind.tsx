import React from "react";
import { styled } from "styled-components";
import Span from "../../../components/span";
import { Button } from "../containers/nav/styles";
import { Input } from "../../../components/input";

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
  margin-top : 10px ;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
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
`

const FindFriend = () => {

  
  
  return (
    <Container>
      <Header>
        <Title>친구 추가하기</Title>
        <SubTitle>시레#123456 와 같이 사용자명을 사용하여 친구를 추가할 수 있어요</SubTitle>
      </Header>
      <Body>
        <Input></Input>
        <WarningMessage hidden={true}>흠 안되는군요. 사용자명을 올바르게 입력했는지 확인해보세요.</WarningMessage>
      </Body>
      
    </Container>
  );
};

export default FindFriend;
