import React from "react";

import { styled } from "styled-components";
import FindFriend from "../../components/findFreind";
import { useDispatch, useSelector } from "react-redux";
import { ViewState } from "../../../../store/slices/viewState";

export const Container = styled.div`
  width: 800px;
  height: 100%;

  border-right: 1px solid #404147;

  ${({ theme }) => theme.color.backgroundTertiary}
`;

const Main = () => {
  const viewState = useSelector((state: any) => state.viewState) as ViewState;
  const dispatch = useDispatch();

  return (
    <Container>
      {viewState.infos[4].active && <FindFriend></FindFriend>}
    </Container>
  );
};

export default Main;
