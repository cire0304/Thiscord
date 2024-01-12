import { Dispatch, useEffect, useState } from "react";
import { styled } from "styled-components";

import * as S from "./styles";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faComment } from "@fortawesome/free-regular-svg-icons";

import { useDispatch } from "react-redux";
import FriendAPI, { GetFriendResponse } from "../../../api/friend";
import RoomAPI from "../../../api/room";
import { setRoomInfoState } from "../../../store";

export default function ShowFriend() {
  const [getFriendResponse, setGetFriendResponse] =
    useState<GetFriendResponse>();
  const [requestCount, setRequestCount] = useState<number>(0);
  const dispatch = useDispatch();

  useEffect(() => {
    const getFriendListRequest = async () => {
      const res = await FriendAPI.getFriendList();
      if (res.status !== 200) {
        alert(res.data);
        return;
      }
      setGetFriendResponse(res.data);
      setRequestCount(res.data.friends.length);
    };
    getFriendListRequest();
  }, []);

  return (
    <Container>
      <S.Header>
        <S.Title> 모든 친구 - {requestCount}명</S.Title>
      </S.Header>

      <S.Body>
        {getFriendResponse &&
          getFriendResponse.friends?.map((element) => {
            return (
              <S.Friend>
                <S.Info>
                  <S.Nickname>{element.nickname}</S.Nickname>
                  <S.Type>현재 로그인 상태를 출력해야 함(개발 예정)</S.Type>
                </S.Info>
                <S.Button
                  onClick={() => {
                    createRoomHandler(element.userId, dispatch);
                  }}
                >
                  <FontAwesomeIcon icon={faComment} />
                </S.Button>
              </S.Friend>
            );
          })}
      </S.Body>
    </Container>
  );
}

async function createRoomHandler(receiverId: number, dispatch: Dispatch<any>) {
  let res = await RoomAPI.createDmRoom(receiverId);
  if (res.status !== 200) {
    console.log(res);
    alert(res.data);
  }
  res = await RoomAPI.getRoomList();

  // Is this rigth way to use redux?
  // replace with another way like socket.io
  dispatch(setRoomInfoState(res.data));
}

const Container = styled.div`
  padding: 20px 20px;
`;
