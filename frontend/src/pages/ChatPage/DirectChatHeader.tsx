import { styled } from "styled-components";
import Nav from "./components/nav";
import ProfileImage from "../../components/profileImage";
import Span from "../../components/span";

import { DmRoom } from "../../services/RoomService";

export default function DirectChatHeader({room}: {room?:DmRoom}) {
  if (!room) return (<></>);  
  return (
    <>
      <Nav room={room}></Nav>
        <Header>
          <Profile>
            <ProfileImage
              src={`https://gravatar.com/avatar/${room.otherUser.userId}?d=identicon`}
              size="60px"
            />
            <Nickname>{room.otherUser.nickname}</Nickname>
            <Nickname>{room.otherUser.nickname}</Nickname>
            <NicknameAndCode>{`${room.otherUser.nickname}#${room.otherUser.userCode}`}</NicknameAndCode>
          </Profile>
        </Header>
      </>
  );
}


const Header = styled.div`
  height: auto;
  width: 100%;

  padding: 10px;
  border-bottom: 1px solid #3a3a3d;
`;

const Profile = styled.div`
  width: 100%;
  height: auto;

  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
  gap: 10px;
`;

const Nickname = styled(Span)`
  font-size: 24px;
  color: white;
`;

const NicknameAndCode = styled(Span)`
  font-size: 20px;
  color: white;
`;

