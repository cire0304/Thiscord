import axios from "axios";
import { CHAT_SERVER_URL } from "../constants/constants";

const axiosInstance = axios.create({
  baseURL: CHAT_SERVER_URL,
  withCredentials: true,
});

axiosInstance.interceptors.response.use(
  (response) => {
    console.log(response);
    return response;
  },
  (error) => {
    if (error.response === undefined) {
      console.log("요청에 실패했습니다.");
      console.log(error);
      return error;
    }
    console.log(error.response);
    return error.response;
  }
);

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
  return await axiosInstance.get<ChatMessageHitory[]>(`/rooms/${roomId}`);
};

const ChatAPI = { getChatHistories };

export default ChatAPI;
