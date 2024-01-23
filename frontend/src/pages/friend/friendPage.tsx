import { useSelector } from "react-redux";
import { styled } from "styled-components";
import { ViewState } from "../../store/slices/viewStateSlice";
import ShowOnlineFriend from "./components/showOnlineFriend";
import ShowFriend from "./components/showAllFriend";
import RequestFreind from "./components/showRequestFreind";
import AddFriend from "./components/addFreind";
import Nav from "./components/nav";

export default function FriendPage() {
  const viewState = useSelector((state: any) => state.viewState) as ViewState;

  return (
    <Container>
      <Nav></Nav>
      <Content>
        {viewState.infos[0].active && <ShowOnlineFriend></ShowOnlineFriend>}
        {viewState.infos[1].active && <ShowFriend></ShowFriend>}
        {viewState.infos[2].active && <RequestFreind></RequestFreind>}
        {viewState.infos[4].active && <AddFriend></AddFriend>}
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
