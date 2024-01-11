import { useState } from "react";
import { styled } from "styled-components";
import Profile from "../../../../../assets/images/discodeProfile.jpg";
import ProfileImage from "./profileImage";
import Span from "../../../../../components/span";
import RoomRequest from "../../../../../api/room";

async function exitRoom(roomId: number) {
  const res = await RoomRequest.exitRoom(roomId);
  if (res.status === 200) {
    window.location.reload();
  } else {
    alert(res.data);
  }
}


const DirectRoom = ({
  nickname,
  roomId,
}: {
  nickname: string;
  roomId: number;
}) => {
  const [deleteButtonVisible, setDeleteButtonVisible] = useState(false);

  return (
    <Container>
      <OuterWrapper
        onMouseOver={() => setDeleteButtonVisible(true)}
        onMouseLeave={() => setDeleteButtonVisible(false)}
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
};

export default DirectRoom;

const Container = styled.div`
  width: 100%;
  height: 50px;
  padding: 5px 10px;

  ${({ theme }) => theme.flex.rowStartCenter};
`;

const OuterWrapper = styled.div`
  width: 100%;
  padding: 10px 5px;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  border-radius: 5px;

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
