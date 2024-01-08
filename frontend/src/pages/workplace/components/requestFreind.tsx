import React, { useEffect, useState } from "react";
import { styled } from "styled-components";
import Span from "../../../components/span";
import FriendReqeust, { GetFriendResponse } from "../../../api/friend";
import { useSelector } from "react-redux";
import { UserInfo } from "../../../api/user";
import * as S from "./styles";

export default function RequestFreind() {
  const [friend, setFriend] = useState<GetFriendResponse>();
  const [requestCount, setRequestCount] = useState<number>(0);
  const user = useSelector((state: any) => state.user) as UserInfo;

  useEffect(() => {
    const getFriendListRequest = async () => {
      const res = await FriendReqeust.getFriendList();
      setFriend(res.data);

      let count = 0;
      res.data.friends.forEach((element) => {
        if (element.state === "REQUEST") count++;
      });
      setRequestCount(count);
    };
    getFriendListRequest();
  }, []);

  return (
    <S.Container>
      <S.Header>
        <S.Title>대기 중 - {requestCount}명</S.Title>
      </S.Header>

      <S.Body>
        {friend && friend.friends?.map((element) => {
          if (element.state !== "REQUEST") return;

          if (user.id === element.senderId) {
            return (
              <S.Friend>
                <S.Info>
                  <S.Nickname>{element.receiverNickname}</S.Nickname>
                  <S.Type>보낸 친구 요청</S.Type>
                </S.Info>
                <S.Button onClick={() => rejectFriendRequest(element.id)}>
                  X
                </S.Button>
              </S.Friend>
            );
          } else {
            return (
              <S.Friend>
                <S.Info>
                  <S.Nickname>{element.senderNickname}</S.Nickname>
                  <S.Type>받은 친구 요청</S.Type>
                </S.Info>
                <S.Button onClick={() => acceptFriendRequest(element.id)}>
                  O
                </S.Button>
                <S.Button onClick={() => rejectFriendRequest(element.id)}>
                  X
                </S.Button>
              </S.Friend>
            );
          }
        })}
      </S.Body>
    </S.Container>
  );
}

const acceptFriendRequest = async (id: number) => {
  const res = await FriendReqeust.acceptFriend(id);
  res.status === 200 && window.location.reload();
};

const rejectFriendRequest = async (id: number) => {
  const res = await FriendReqeust.rejectFriend(id);
  res.status === 200 && window.location.reload();
};
