import { useSelector } from "react-redux";
import { styled } from "styled-components";
import { GetRoomListResponse } from "../../../api/room";
import DirectMessageRoom from "./directMessageRoom";

export default function MessageRooms() {
  const roomList = useSelector(
    (state: any) => state.room
  ) as GetRoomListResponse;
  // 방 리덕스 스토어 사용해서 업데이트하는 코드 개발하기

  return (
    <Container>
      <Title>그룹 및 메세지</Title>
      <RoomsWrapper>
        {roomList?.roomDmInfos.map((room, index) => {
          // add GroupRoom component here later
          return (
            <DirectMessageRoom
              key={index}
              nickname={room.otherUserNickname}
              roomId={room.roomId}
            ></DirectMessageRoom>
          );
        })}
        {/* <GroupRoom nickname="이동준" userCount={1}></GroupRoom> */}
      </RoomsWrapper>
    </Container>
  );
}

const Container = styled.div`
  min-width: 250px;

  height: 100%;

  ${({ theme }) => theme.flex.columnStartCenter}
  ${({ theme }) => theme.color.backgroundPrimary}
`;

const Title = styled.span`
  padding: 10px 15px;
  ${({ theme }) => theme.color.secondary}
  font-weight: 700;
  align-self: start;
`;

const RoomsWrapper = styled.div`
  width: 100%;
  flex-grow: 1;
  ${({ theme }) => theme.flex.columnStartCenter}
`;
