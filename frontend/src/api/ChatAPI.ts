import axiosInstance from "./axios";

export interface ChatMessage {
    roomId: number;
    senderId: number;
    content: string;
    messageType: "TALK" | "ENTER" | "EXIT";
    sentDateTime: string;
}

export interface ChatMessageHitory {
  message: {
    roomId: number;
    content: string;
    messageType: string;
    sentDateTime: string;
  };
  user: { id: number; nickname: string; userCode: string };
}

const getChatHistories = async (roomId: number) => {
  return await axiosInstance.get<ChatMessageHitory[]>(`/chat/rooms/${roomId}`);
};

const ChatAPI = { getChatHistories };

export default ChatAPI;
