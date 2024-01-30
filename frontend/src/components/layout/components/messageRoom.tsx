import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAppDispatch, useAppSelector } from "../../../hooks/redux";
import {
  DmRoom,
  GroupRoom,
  RoomService,
  RoomType,
} from "../../../services/RoomService";
import RoomAPI from "../../../api/roomAPI";
import { setCurrentChatRoom, setCurrentDmChatRoom } from "../../../store";
import { css, styled } from "styled-components";
import Span from "../../span";
import ProfileImage from "../../profileImage";

function MessageRoom({
  room,
  roomType,
}: {
  room: DmRoom | GroupRoom;
  roomType: RoomType;
}) {
  const navigate = useNavigate();
  const dispatch = useAppDispatch();

  const [deleteButtonVisible, setDeleteButtonVisible] = useState(false);
  const chatRoom = useAppSelector((state) => state.chatRoom);

  const changeChatRoom = (room: DmRoom | GroupRoom) => {
    dispatch(setCurrentChatRoom({room, roomType}));
    navigate(`/workspace/rooms/${room.roomId}`);
  };

  const exitRoom = async (roomId: number) => {
    const res = await RoomAPI.exitRoom(roomId);
    if (res.status !== 200) {
      alert(res.data);
    } else if (chatRoom.currentRoomId === room.roomId) {
      dispatch(RoomService.getRoomList());
      navigate(`/workspace/me`);
    }
  };

  if (roomType === RoomType.GROUP) {
    room = room as GroupRoom;
    return (
      <Container>
        <OuterWrapper
          onMouseOver={() => setDeleteButtonVisible(true)}
          onMouseLeave={() => setDeleteButtonVisible(false)}
          onClick={() => changeChatRoom(room)}
          $active={chatRoom.currentDmChatRoom?.roomId === room.roomId}
        >
          <ProfileImage
            src={`https://gravatar.com/avatar/${room.roomId}?d=identicon`}
          />
          <InfoWrapper>
            <Nickname>{room.groupName}</Nickname>
          </InfoWrapper>
          {deleteButtonVisible && (
            <Button onClick={() => exitRoom(room.roomId)}>X</Button>
          )}
        </OuterWrapper>
      </Container>
    );
  }
  
  room = room as DmRoom;
  return (
    <Container>
      <OuterWrapper
        onMouseOver={() => setDeleteButtonVisible(true)}
        onMouseLeave={() => setDeleteButtonVisible(false)}
        onClick={() => changeChatRoom(room)}
        $active={chatRoom.currentDmChatRoom?.roomId === room.roomId}
      >
        <ProfileImage
          src={`https://gravatar.com/avatar/${room.otherUser.userId}?d=identicon`}
        />
        <InfoWrapper>
          <Nickname>{room.otherUser.nickname}</Nickname>
        </InfoWrapper>
        {deleteButtonVisible && (
          <Button onClick={() => exitRoom(room.roomId)}>X</Button>
        )}
      </OuterWrapper>
    </Container>
  );
}

export default MessageRoom;

const Container = styled.div`
  width: 100%;
  height: 50px;
  padding: 5px 10px;

  ${({ theme }) => theme.flex.rowStartCenter};
`;

const OuterWrapper = styled.div<{ $active: boolean }>`
  width: 100%;
  padding: 10px 5px;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  border-radius: 5px;

  ${(props) =>
    props.$active &&
    css`
      background-color: #36393f;
    `}

  &:hover {
    background-color: #36393f;
  }

  &:active {
    background-color: #4a4d53;
  }
`;

const InfoWrapper = styled.div`
  ${({ theme }) => theme.flex.columnCenterStart};
  flex-grow: 1;
  padding-left: 5px;
  margin-left: 5px;
`;

const Nickname = styled(Span)`
  ${({ theme }) => theme.color.secondary};
`;

const Button = styled.button`
  background-color: transparent;
  border: none;
  outline: none;

  color: #bdbdbd;
`;
