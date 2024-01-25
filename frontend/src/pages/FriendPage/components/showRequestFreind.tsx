import React, { useEffect, useState } from "react";

import * as S from "./styles";
import FriendAPI, { GetFriendRequestResponse } from "../../../api/friendAPI";

export default function RequestFreind() {
  const [friendRequestResponse, setFriendRequestResponse] =
    useState<GetFriendRequestResponse>();
  const [requestCount, setRequestCount] = useState<number>(0);

  useEffect(() => {
    friendRequest();
  }, []);

  const acceptFriendRequest = async (id: number) => {
    const res = await FriendAPI.acceptFriend(id);
    if (res.status !== 200) return null;
    friendRequest();
  };

  const rejectFriendRequest = async (id: number) => {
    const rejectRes = await FriendAPI.rejectFriend(id);
    if (rejectRes.status !== 200) return null;
    friendRequest();
  };

  const friendRequest = async () => {
    const res = await FriendAPI.getFriendRequestList();
      if (res.status !== 200) {
        alert(res.data);
        return;
      }
      setFriendRequestResponse(res.data);

      let count =
        res.data.receivedFriendRequests.length +
        res.data.sentFriendRequests.length;
      setRequestCount(count);
  }

  return (
    <S.Container>
      <S.Header>
        <S.Title>대기 중 - {requestCount}명</S.Title>
      </S.Header>

      <S.Body>
        {friendRequestResponse &&
          friendRequestResponse.receivedFriendRequests?.map((element) => {
            return (
              <S.Friend>
                <S.Info>
                  <S.Nickname>{element.nickname}</S.Nickname>
                  <S.Type>받은 친구 요청</S.Type>
                </S.Info>
                <S.Button
                  onClick={() => {
                    acceptFriendRequest(element.id);
                  }}
                >
                  O
                </S.Button>
                <S.Button
                  onClick={() => {
                    rejectFriendRequest(element.id);
                  }}
                >
                  X
                </S.Button>
              </S.Friend>
            );
          })}
        {friendRequestResponse &&
          friendRequestResponse.sentFriendRequests?.map((element) => {
            return (
              <S.Friend>
                <S.Info>
                  <S.Nickname>{element.nickname}</S.Nickname>
                  <S.Type>보낸 친구 요청</S.Type>
                </S.Info>
                <S.Button
                  onClick={() => {
                    rejectFriendRequest(element.id);
                  }}
                >
                  X
                </S.Button>
              </S.Friend>
            );
          })}
      </S.Body>
    </S.Container>
  );
}
