import { Dispatch, useEffect, useState } from "react";
import { styled } from "styled-components";

import * as S from "./styles";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faComment } from "@fortawesome/free-regular-svg-icons";

import FriendAPI, { GetFriendResponse } from "../../../api/friendAPI";
import RoomAPI from "../../../api/roomAPI";
import { setCurrentChatRoomId } from "../../../store";
import { useNavigate } from "react-router-dom";
import { useAppDispatch } from "../../../hooks/redux";
import { RoomService } from "../../../services/RoomService";

export default function ShowFriend() {
  const [getFriendResponse, setGetFriendResponse] =
    useState<GetFriendResponse>();
  const [requestCount, setRequestCount] = useState<number>(0);
  const dispatch = useAppDispatch();

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

  const navigate = useNavigate();

  const createRoomHandler = async (
    receiverId: number,
    dispatch: Dispatch<any>
  ) => {
    let res = await RoomAPI.createDmRoom(receiverId);
    if (res.status !== 200) {
      alert(res.data);
    }
    dispatch(setCurrentChatRoomId(res.data));

    dispatch(RoomService.getRoomList());
    navigate(`/workspace/rooms/${res.data.roomId}`);
  };

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

const Container = styled.div`
  padding: 20px 20px;
`;
