import { styled } from "styled-components";
import ShowOnlineFriend from "./components/showOnlineFriend";
import ShowFriend from "./components/showAllFriend";
import RequestFreind from "./components/showRequestFreind";
import AddFriend from "./components/addFreind";

import { useAppSelector } from "../../hooks/redux";
import Nav from "./container/nav";

export default function FriendPage() {
  const viewState = useAppSelector((state) => state.viewState);

  return (
    <Container>
      <Nav></Nav>
      <Content>
        {/* {viewState.infos[0].active && <ShowOnlineFriend></ShowOnlineFriend>} */}
        {viewState.infos[0].active && <ShowFriend></ShowFriend>}
        {viewState.infos[1].active && <RequestFreind></RequestFreind>}
        {/* 차단 목록 */}
        {viewState.infos[3].active && <AddFriend></AddFriend>}
      </Content>
    </Container>
  );
}

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
