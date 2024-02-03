import { styled } from "styled-components";
import Span from "../../span";
import { useAppSelector } from "../../../hooks/redux";
import MessageRoom from "./messageRoom";
import { RoomType } from "../../../services/RoomService";

export default function MessageRoomSide() {
  const rooms = useAppSelector((state) => state.room.rooms);

  return (
    <Container>
      <Title>그룹 및 메세지</Title>
      <RoomsWrapper>
        {rooms?.groupRooms.map((room, index) => {
          return (
            <MessageRoom
              key={index}
              room={room}
              roomType={RoomType.GROUP}
            ></MessageRoom>
          );
        })}
        {rooms?.dmRooms.map((room, index) => {
          return (
            <MessageRoom
              key={index}
              room={room}
              roomType={RoomType.DM}
            ></MessageRoom>
          );
        })}
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
