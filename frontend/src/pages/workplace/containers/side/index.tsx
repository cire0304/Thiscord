import React from "react";
import * as S from "./styles";
import ControlBar from "./components/controlBar";

import GroupRoom from "./components/groupRoom";
import DirectRoom from "./components/directRoom";

const Side = () => {
  return (
    <S.Container>
      <S.Title>그룹 및 메세지</S.Title>
      <S.RoomsWrapper>
        <GroupRoom nickname="이동준" userCount={1}></GroupRoom>
        <DirectRoom nickname="시레"></DirectRoom>
      </S.RoomsWrapper>
      <ControlBar nickname="asdf" userCode={123}></ControlBar>
    </S.Container>
  );
};

export default Side;
