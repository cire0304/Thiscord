import { styled } from "styled-components";
import { GetRoomListResponse } from "../../../api/roomAPI";
import DirectMessageRoom from "./directMessageRoom";
import Span from "../../span";
import { useAppSelector } from "../../../hooks/redux";

export default function MessageRooms() {
  const rooms = useAppSelector( (state) => state.room.rooms);

  return (
    <Container>
      <Title>그룹 및 메세지</Title>
      <RoomsWrapper>
        {rooms?.dmRooms.map((room, index) => {
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
