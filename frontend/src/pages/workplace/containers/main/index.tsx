import React from "react";

import { styled } from "styled-components";
import AddFriend from "../../components/addFreind";
import { useDispatch, useSelector } from "react-redux";
import { ViewState } from "../../../../store/slices/viewState";
import RequestFreind from "../../components/requestFreind";
import ShowFriend from "../../components/showFriend";
import ShowOnlineFriend from "../../components/showOnlineFriend";

export const Container = styled.div`
  width: 800px;
  height: 100%;

  border-right: 1px solid #404147;

  ${({ theme }) => theme.color.backgroundTertiary}
`;

const Main = () => {
  const viewState = useSelector((state: any) => state.viewState) as ViewState;

  return (
    <Container>
      {viewState.infos[0].active && <ShowOnlineFriend></ShowOnlineFriend>}
      {viewState.infos[1].active && <ShowFriend></ShowFriend>}
      {viewState.infos[2].active && <RequestFreind></RequestFreind>}
      {viewState.infos[4].active && <AddFriend></AddFriend>}
    </Container>
  );
};

export default Main;
