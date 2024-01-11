import React from 'react'
import { styled } from 'styled-components';
import Nav from './components/nav';



export default function ChatPage() {
  return (
    <Container>
      <Nav></Nav>
      <Content></Content>
    </Container>
  )
}

// Below code is duplicated from src/pages/friend/friendPage.tsx:
const Container = styled.div`
width: 800px;
  height: auto;

  border-right: 1px solid #404147;

  ${({ theme }) => theme.color.backgroundTertiary}
`;

const Content = styled.div`
  width: 100%;
  height: auto;
`;