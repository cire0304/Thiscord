import React, { Dispatch, SetStateAction, useEffect, useState } from "react";

import * as S from "./styles";
import ControlBar from "./components/controlBar";

import GroupRoom from "./components/groupRoom";
import DirectRoom from "./components/directRoom";
import UserRequest from "../../../../api/user";
import { useDispatch, useSelector } from "react-redux";
import { setUserInfoState } from "../../../../store";


const Side = ({setIsProfileModalActive} : {setIsProfileModalActive: Dispatch<SetStateAction<boolean>>}) => {
  const dispatch = useDispatch();
  useEffect(() => {
    const getUserInfo = async () => {
      const res = await UserRequest.getUserInfo()
      res.data && dispatch(setUserInfoState(res.data));
    }
    getUserInfo();
  }, []);

  return (
    <S.Container>
      <S.Title>그룹 및 메세지</S.Title>
      <S.RoomsWrapper>
        <GroupRoom nickname="이동준" userCount={1}></GroupRoom>
        <DirectRoom nickname="시레"></DirectRoom>
      </S.RoomsWrapper>
      <ControlBar setIsProfileModalActive={setIsProfileModalActive}></ControlBar>
    </S.Container>
  );
};

export default Side;
