import React from "react";
import * as S from "./styles";
import { ReactComponent as ChannelIcon } from "../../../../assets/icons/channel.svg";
import { useDispatch, useSelector } from "react-redux";
import { ViewState } from "../../../../store/slices/viewState";
import { activeById } from "../../../../store";

const Nav = () => {
  const dispatch = useDispatch();
  const viewState = useSelector((state: any) => state.viewState) as ViewState;

  return (
    <S.Container>
      <ChannelIcon />
      <S.Text>친구</S.Text>

      <S.Navigates>
        {viewState.infos.map((item, index) => {
          return (
            <S.Button
              key={index}
              active={viewState.infos[index].active}
              onClick={() => {
                dispatch(activeById({ id: index }));
              }}
            >
              {item.name}
            </S.Button>
          );
        })}
      </S.Navigates>
    </S.Container>
  );
};

export default Nav;
