import { useEffect, useState } from "react";
import { css, styled } from "styled-components";
import Profile from "../../../assets/images/discodeProfile.jpg";
import RoomAPI from "../../../api/roomAPI";
import Span from "../../span";
import ProfileImage from "../../profileImage";
import { useDispatch, useSelector } from "react-redux";
import { setCurrentChatRoomId } from "../../../store";
import { useNavigate } from "react-router-dom";


async function exitRoom(roomId: number) {
  const res = await RoomAPI.exitRoom(roomId);
  if (res.status === 200) {
    window.location.reload();
  } else {
    alert(res.data);
  }
}

export default function DirectMessageRoom({
  nickname,
  roomId,
}: {
  nickname: string;
  roomId: number;
}) {
  const [deleteButtonVisible, setDeleteButtonVisible] = useState(false);
  const navigate = useNavigate();

  const dispatch = useDispatch();
  const changeChatRoom = (roomId: number) => {
    dispatch(setCurrentChatRoomId(roomId));
    navigate(`/workspace/rooms/${roomId}`)
  };

  const chatRoom = useSelector((state: any) => state.chatRoom);

  return (
    <Container>
      <OuterWrapper
        onMouseOver={() => setDeleteButtonVisible(true)}
        onMouseLeave={() => setDeleteButtonVisible(false)}
        onClick={() => changeChatRoom(roomId)}
        $active={chatRoom.currentChatRoom.id === roomId}
      >
        <ProfileImage src={Profile} />
        <InfoWrapper>
          <Nickname>{nickname}</Nickname>
        </InfoWrapper>
        {deleteButtonVisible && (
          <Button onClick={() => exitRoom(roomId)}>X</Button>
        )}
      </OuterWrapper>
    </Container>
  );
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
