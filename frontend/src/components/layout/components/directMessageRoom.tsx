import { useState } from "react";
import { css, styled } from "styled-components";
import RoomAPI, { DmRoom } from "../../../api/roomAPI";
import Span from "../../span";
import ProfileImage from "../../profileImage";
import { setCurrentChatRoomId, setRoomInfoState } from "../../../store";
import { useNavigate } from "react-router-dom";
import { ChatRoomState } from "../../../store/slices/chatRoomSlice";
import { useAppDispatch, useAppSelector } from "../../../hooks/redux";
import { RoomService } from "../../../services/RoomService";

export default function DirectMessageRoom({ room }: { room: DmRoom }) {
  const [deleteButtonVisible, setDeleteButtonVisible] = useState(false);
  const navigate = useNavigate();

  // this code is to set current chat room id and used in another component.
  // TODO: So, this code should be refactored.
  const dispatch = useAppDispatch();
  const changeChatRoom = (room: DmRoom) => {
    dispatch(setCurrentChatRoomId(room));
    // TODO: extract url to .env file
    navigate(`/workspace/rooms/${room.roomId}`);
  };

  const chatRoom = useSelector((state: any) => state.chatRoom) as ChatRoomState;

  return (
    <Container>
      <OuterWrapper
        onMouseOver={() => setDeleteButtonVisible(true)}
        onMouseLeave={() => setDeleteButtonVisible(false)}
        onClick={() => changeChatRoom(room)}
        $active={chatRoom.currentChatRoom.roomId === room.roomId}
      >
        <ProfileImage
          src={`https://gravatar.com/avatar/${room.otherUserId}?d=identicon`}
        />
        <InfoWrapper>
          <Nickname>{room.otherUserNickname}</Nickname>
        </InfoWrapper>
        {deleteButtonVisible && (
          <Button onClick={() => exitRoom(room.roomId)}>X</Button>
        )}
      </OuterWrapper>
    </Container>
  );
}

async function exitRoom(roomId: number) {
  const res = await RoomAPI.exitRoom(roomId);
  if (res.status === 200) {
    window.location.reload();
  } else {
    alert(res.data);
  }
}

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
