import { styled } from "styled-components";

import MessageInputTextarea from "./components/MessageForm";
import MessageHistory from "./components/MessageHistory";
import { useAppSelector } from "../../hooks/redux";

import DirectChatHeader from "./components/DirectChatHeader";
import GroupChatHeader from "./components/GroupChatHeader";
import { useChatRoom } from "../../hooks/useChatRoom";

export default function ChatPage() {
  const room = useAppSelector((state) => state.chatRoom);
  const myUserInfo = useAppSelector((state) => state.user);
  const {chatHistory, sendHandler} = useChatRoom();

  return (
    <Container>
      <Content>
         {room.currentRoomType === "LODING" ? <></> :
          room.currentRoomType === "DM" ?
          <DirectChatHeader room={room.currentDmChatRoom}></DirectChatHeader> :
          <GroupChatHeader room={room.currentGroupChatRoom}></GroupChatHeader>
         }

        <Body>
          <MessageHistory chatHistories={chatHistory}></MessageHistory>
        </Body>

      </Content>
      <Footer>
        <MessageInputTextarea
          onMessageSend={sendHandler}
          roomId={room.currentRoomId}
          user={myUserInfo}
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

  background-color: #303338;
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

const Body = styled.div`
  width: 100%;
  flex-grow: 1;
`;

const Footer = styled.div`
  padding: 20px;
  width: 100%;
  height: fit-content;

  position: absolute;
  bottom: 0;
`;
