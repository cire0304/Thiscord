import React, { Dispatch, SetStateAction, useEffect, useState } from "react";
import * as S from "./styles";
import ControlBar from "./components/controlBar";

import GroupRoom from "./components/groupRoom";
import DirectRoom from "./components/directRoom";
import UserRequest from "../../../../api/user";


interface UserInfo {
  id: number;
  email: string;
  nickname: string;
  userCode: string;
}

const Side = ({setIsProfileModalActive} : {setIsProfileModalActive: Dispatch<SetStateAction<boolean>>}) => {

  const [userInfo, setUserInfo] = useState<UserInfo>({
    id: 0,
    email: "로딩 중...",
    nickname: "로딩 중...",
    userCode: "",
  });

  useEffect(() => {
    const getUserInfo = async () => {
      const res = await UserRequest.getUserInfo()
      res.data && setUserInfo(res.data);
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
      <ControlBar nickname={userInfo.nickname} userCode={userInfo.userCode} setIsProfileModalActive={setIsProfileModalActive}></ControlBar>
    </S.Container>
  );
};

export default Side;
