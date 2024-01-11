import React, { useEffect, useState } from "react";

import * as S from "./styles";
import FriendReqeust, { GetFriendRequestResponse } from "../../../api/friend";

export default function RequestFreind() {
  const { friendRequestResponse, requestCount } = useFriendRequest();
  const [reRender, setReRender] = useState<boolean>(false);

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

function useFriendRequest(): {
  friendRequestResponse?: GetFriendRequestResponse;
  requestCount?: number;
} {
  const [friendRequestResponse, setFriendRequestResponse] =
    useState<GetFriendRequestResponse>();
  const [requestCount, setRequestCount] = useState<number>(0);

  useEffect(() => {
    const getFriendListRequest = async () => {
      const res = await FriendReqeust.getFriendRequestList();
      if (res.status !== 200) {
        alert(res.data);
        return;
      }
      setFriendRequestResponse(res.data);

      let count =
        res.data.receivedFriendRequests.length +
        res.data.sentFriendRequests.length;
      setRequestCount(count);
    };
    getFriendListRequest();
  }, []);

  return { friendRequestResponse, requestCount };
}

const acceptFriendRequest = async (id: number) => {
  const res = await FriendReqeust.acceptFriend(id);
  res.status === 200 && window.location.reload();
};

const rejectFriendRequest = async (id: number) => {
  const res = await FriendReqeust.rejectFriend(id);
  res.status === 200 && window.location.reload();
};
