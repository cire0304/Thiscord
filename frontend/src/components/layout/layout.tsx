import React, { useEffect } from "react";

import UserGuard from "../../utils/userGuard";
import FetchRoomList from "../../utils/fetchRoomList";
import styled from "styled-components";
import { Input } from "../input";

import MessageRooms from "./components/messageRooms";
import ProfileModal from "./modal/profileModal";

import { Outlet } from "react-router-dom";
import ControlBar from "./components/controlBar";

const TestPage = () => {
  const [isProfileModalActive, setIsProfileModalActive] =
    React.useState<boolean>(false);

  return (
    <UserGuard>
      <FetchRoomList>
        <Container>
          <Side>
            <Header>
              <Input placeholder="대화 상대 찾기 또는 시작하기"></Input>
            </Header>
            <Body>
              <MessageRooms />
            </Body>
            <Footer>
              <ControlBar setIsProfileModalActive={setIsProfileModalActive} />
            </Footer>
          </Side>

          {isProfileModalActive && (
            <ProfileModal
              setIsProfileModalActive={setIsProfileModalActive}
              isProfileModalActive={isProfileModalActive}
            />
          )}

          <Main>
            <Outlet />
          </Main>
        </Container>
      </FetchRoomList>
    </UserGuard>
  );
};

export default TestPage;

export const Container = styled.div`
  width: 100vw;
  height: 100vh;

  position: relative;

  display: flex;
  flex-direction: row;
  justify-content: flex-start;
`;

const Side = styled.div`
  min-width: 250px;

  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
`;

export const Header = styled.header`
  width: 100%;
  height: 50px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  padding: 10px;

  ${({ theme }) => theme.color.backgroundPrimary}
  border-bottom: 1px solid ${({ theme }) => theme.color.border};
`;

export const Body = styled.body`
  width: 100%;
  flex-grow: 1;
  ${({ theme }) => theme.flex.rowStartCenter}
  ${({ theme }) => theme.color.backgroundPrimary}
`;

export const Footer = styled.footer`
  max-width: 250px;
  width: 100%;
  height: 50px;
  padding: 0px 10px;

  /* ${({ theme }) => theme.flex.rowCenterCenter}; */
  ${({ theme }) => theme.color.backgroundSecondary};
`;

export const Main = styled.main`
  width: 100%;
  flex-grow: 1;
  display: flex;
  ${({ theme }) => theme.color.backgroundTertiary}
`;
