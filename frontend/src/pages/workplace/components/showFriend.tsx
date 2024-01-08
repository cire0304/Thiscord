import React, { useEffect, useState } from "react";
import { styled } from "styled-components";
import FriendReqeust, { GetFriendResponse } from "../../../api/friend";
import { useSelector } from "react-redux";
import { UserInfo } from "../../../api/user";
import * as S from "./styles";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faComment, faMessage } from "@fortawesome/free-regular-svg-icons";

export default function ShowFriend() {
  const [friend, setFriend] = useState<GetFriendResponse>();
  const [requestCount, setRequestCount] = useState<number>(0);
  const user = useSelector((state: any) => state.user) as UserInfo;

  useEffect(() => {
    const getFriendListRequest = async () => {
      const res = await FriendReqeust.getFriendList();
      setFriend(res.data);

      let count = 0;
      res.data.friends.forEach((element) => {
        console.log(element);

        if (element.state === "ACCEPT") count++;
      });
      setRequestCount(count);
    };
    getFriendListRequest();
  }, []);

  return (
    <Container>
      <S.Header>
        <S.Title> 모든 친구 - {requestCount}명</S.Title>
      </S.Header>

      <S.Body>
        {friend &&
          friend.friends?.map((element) => {
            if (element.state !== "ACCEPT") return;
            return (
              <S.Friend>
                <S.Info>
                  <S.Nickname>{element.receiverNickname}</S.Nickname>
                  <S.Type>현재 로그인 상태를 출력해야 함(개발 예정)</S.Type>
                </S.Info>
                <S.Button >
                    {/* <FontAwesomeIcon icon={fa-egular fa-comment} /> */}
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

