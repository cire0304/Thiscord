import { useSelector } from "react-redux";
import { styled } from "styled-components";
import { GetRoomListResponse } from "../../../api/roomAPI";
import DirectMessageRoom from "./directMessageRoom";
import Span from "../../span";

export default function MessageRooms() {
  const roomList = useSelector(
    (state: any) => state.room
  ) as GetRoomListResponse;
  //TODO: 방 리덕스 스토어 사용해서 업데이트하는 코드 개발하기

  return (
    <Container>
      <Title>그룹 및 메세지</Title>
      <RoomsWrapper>
        {roomList?.rooms.map((room, index) => {
          // TODO: add GroupRoom component here later
          return (
            <DirectMessageRoom
              key={index}
              room={room}
            ></DirectMessageRoom>
          );
        })}
        {/* TODO: develop for groupRoom */}
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

const Title = styled(Span)`
  padding: 10px 15px;
  ${({ theme }) => theme.color.secondary}
  align-self: start;
`;

const RoomsWrapper = styled.div`
  width: 100%;
  flex-grow: 1;
  ${({ theme }) => theme.flex.columnStartCenter}
`;
