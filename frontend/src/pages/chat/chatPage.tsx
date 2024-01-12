import { useEffect, useState } from "react";
import { styled } from "styled-components";
import Nav from "./components/nav";
import ProfileImage from "../../components/profileImage";
import Span from "../../components/span";
import { useSelector } from "react-redux";
import RoomAPI, { DmRoom, RoomUser } from "../../api/roomAPI";

export default function ChatPage() {
  const room = useSelector((state: any) => state.chatRoom.currentChatRoom) as DmRoom;
  const [roomUser, setRoomUser] = useState<RoomUser>();
  
  useEffect(() => {
    const fetchRoomUser = async () => {
      const res = await RoomAPI.getDmRoomUser(room.roomId, room.otherUserId)
      if (res.status !== 200) {
        alert(res.data);
        return;
      }
      setRoomUser(res.data);
    };
    fetchRoomUser();
  });

  return (
    <Container>
      <Nav room={room}></Nav>
      <Content>
        <Header>
          <Profile>
            <ProfileImage
              src={`https://gravatar.com/avatar/${roomUser?.userId}?d=identicon`}
              size="60px"
            />
            <Nickname>{roomUser?.nickname}</Nickname>
            <NicknameAndCode>{`${roomUser?.nickname}#${roomUser?.userCode}`}</NicknameAndCode>
            <Description>
              {`${roomUser?.nickname}님과 다이렉트 메세지의 첫 부분이예요.`}
            </Description>
          </Profile>
        </Header>

        <Body></Body>
      </Content>
    </Container>
  );
}

// Below code is duplicated from src/pages/friend/friendPage.tsx:
const Container = styled.div`
  width: 800px;
  height: auto;

  border-right: 1px solid #404147;

  ${({ theme }) => theme.color.backgroundTertiary}
`;

const Content = styled.div`
  width: 100%;
  height: auto;
`;

const Header = styled.div`
  height: auto;
  width: 100%;

  padding: 10px;

  border-bottom: 1px solid #3a3a3d;

  ${({ theme }) => theme.color.backgroundTertiary};
`;

// i think below code might be converted to component
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

const Description = styled(Span)`
  font-size: 16px;
  ${({ theme }) => theme.color.secondary};
`;
// ===================================================

const Body = styled.div`
  padding: 20px;
  width: 100%;
  height: 100%;
`;
