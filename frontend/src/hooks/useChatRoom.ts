import { useEffect, useRef, useState } from "react";
import { CHAT_SERVER_END_POINT } from "../constants/constants";
import { CompatClient, Stomp } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import ChatAPI, { ChatMessageHitory } from "../api/ChatAPI";
import { getCurrentTime } from "../utils/Dates";
import { useAppSelector } from "./redux";

export function useChatRoom() {
  const [chatHistory, setChatHistory] = useState<ChatMessageHitory[]>([]);
  const currentRoom = useAppSelector((state) => state.chatRoom);
  const currentUserInfo = useAppSelector((state) => state.user);
  const client = useRef<CompatClient | null>(null);

  const roomId = currentRoom.currentRoomId;
  const senderId = currentUserInfo.id;

  useEffect(() => {
    if (currentRoom.currentRoomType === "LODING") return;
    const socket = new SockJS(CHAT_SERVER_END_POINT as string);
    client.current = Stomp.over(socket);

    client.current.connect({}, () => {
      if (roomId === undefined) return;

      ChatAPI.getChatHistories(roomId).then((res) => {
        res.data && setChatHistory(res.data);
      });

      client.current?.subscribe(`/chat/sub/chat/rooms/${roomId}`, (message) => {
        message.body &&
          setChatHistory((prev) => [...prev, JSON.parse(message.body)]);
      });
    });

    return () => {
      client.current?.deactivate();
    };
  }, [currentRoom, roomId]);

  const sendHandler = (message: string) => {
    if (client.current && client.current.connected) {
      client.current.send(
        `/chat/pub/chat/rooms`,
        {},
        JSON.stringify({
          roomId: roomId,
          senderId: senderId,
          content: message,
          messageType: "TALK",
          sentDateTime: getCurrentTime(),
        })
      );
    }
  };

  return { chatHistory, sendHandler };
}
