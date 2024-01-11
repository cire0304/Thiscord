import React, { Dispatch, SetStateAction, useEffect, useState } from "react";

import * as S from "./styles";
import ControlBar from "./components/controlBar";

import GroupRoom from "./components/groupRoom";
import DirectRoom from "./components/directRoom";
import UserRequest from "../../../../api/user";
import { useDispatch, useSelector } from "react-redux";
import { setUserInfoState } from "../../../../store";
import RoomRequest, { GetRoomListResponse } from "../../../../api/room";


const Side = ({setIsProfileModalActive} : {setIsProfileModalActive: Dispatch<SetStateAction<boolean>>}) => {

  // const [roomList, setRoomList] = useState<GetRoomListResponse>();

  const roomList = useSelector((state: any) => state.room) as GetRoomListResponse;
  // 방 리덕스 스토어 사용해서 업데이트하는 코드 개발하기

  return (
    <S.Container>
      <S.Title>그룹 및 메세지</S.Title>
      <S.RoomsWrapper>
        {
          roomList?.roomDmInfos.map((room, index) => {
            // add GroupRoom component here later
            return <DirectRoom key={index} nickname={room.otherUserNickname} roomId={room.roomId} ></DirectRoom>
          })
        }
         {/* <GroupRoom nickname="이동준" userCount={1}></GroupRoom> */}

      </S.RoomsWrapper>
      <ControlBar setIsProfileModalActive={setIsProfileModalActive}></ControlBar>
    </S.Container>
  );
};

export default Side;
