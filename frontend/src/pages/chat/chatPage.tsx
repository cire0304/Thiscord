import { useEffect, useRef, useState } from "react";
import { styled } from "styled-components";
import Nav from "./components/nav";
import ProfileImage from "../../components/profileImage";
import Span from "../../components/span";
import { useSelector } from "react-redux";
import RoomAPI, { DmRoom, RoomUser } from "../../api/roomAPI";
import MessageInputTextarea from "./components/MessageForm";

import SockJS from "sockjs-client";
import { Stomp, CompatClient } from "@stomp/stompjs";
import ChatAPI, { ChatMessageHitory } from "../../api/ChatAPI";
import { useNavigate } from "react-router-dom";
import MessageHistory from "./components/MessageHistory";
import { UserInfo } from "../../api/userAPI";

export default function ChatPage() {
  const room = useSelector(
    (state: any) => state.chatRoom.currentChatRoom
  ) as DmRoom;
  const [roomUser, setRoomUser] = useState<RoomUser>();
  const user = useSelector((state: any) => state.user) as UserInfo;
  const navigate = useNavigate();

  useEffect(() => {
    const fetchRoomUser = async () => {
      if (room.isLoading === false) {
        navigate("/workspace/me");
        return;
      }
      const res = await RoomAPI.getDmRoomUser(room.roomId, room.otherUserId);
      if (res.status !== 200) {
        alert(res.data);
        return;
      }
      setRoomUser(res.data);
    };
    fetchRoomUser();
  }, [room]);

  //================================================================
  const [chatHistory, setChatHistory] = useState<ChatMessageHitory[]>([]);
  const client = useRef<CompatClient | null>(null);

  const connectHandler = () => {
    const socket = new SockJS("http://localhost:8080/ws");
    client.current = Stomp.over(socket);

    // SockJS 연결
    client.current.connect({}, () => {
      // 채팅방 입장
      ChatAPI.getChatHistories(room.roomId).then((res) => {
        res.data && setChatHistory(res.data);
      });

      // 채팅방 구독
      client.current?.subscribe(`/sub/chat/rooms/${room.roomId}`, (message) => {
        message.body &&
          setChatHistory((prev) => [...prev, JSON.parse(message.body)]);
      });
    });
  };

  useEffect(() => {
    connectHandler();
    return () => {
      client.current?.deactivate();
    };
  }, []);

  const sendHandler = (message: string) => {
    if (client.current && client.current.connected) {
      client.current.send(
        `/pub/chat/rooms`,
        {},
        JSON.stringify({
          roomId: room.roomId,
          senderId: user.id,
          content: message,
          messageType: "TALK",
          sentDateTime: new Date().toISOString(),
        })
      );
    }
  };

  //================================================================

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
          </Profile>
        </Header>

        <Body>
          {/* 스타일 고쳐라 */}
          <MessageHistory chatHistories={chatHistory}></MessageHistory>

         
        </Body>
      </Content>
      <Footer>
        <MessageInputTextarea
          onMessageSend={sendHandler}
          nickname={roomUser?.nickname}
        />
      </Footer>
    </Container>
  );
}

const Container = styled.div`
  width: 800px;
  height: 100%;
  border-right: 1px solid #404147;

  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;

  position: relative;

  ${({ theme }) => theme.color.backgroundTertiary}
`;

const Content = styled.div`
  width: 100%;

  margin-bottom: 60px;

  flex-grow: 1;
  overflow-y: scroll;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;

  position: relative;

  &::-webkit-scrollbar {
    display: none;
  }
`;

const Header = styled.div`
  height: auto;
  width: 100%;

  padding: 10px;
  border-bottom: 1px solid #3a3a3d;
  ${({ theme }) => theme.color.backgroundTertiary};
`;

const Body = styled.div`
  width: 100%;
  flex-grow: 1;

  background-color: white;
`;

const Footer = styled.div`
  padding: 20px;
  width: 100%;
  height: fit-content;

  position: absolute;
  bottom: 0;
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
