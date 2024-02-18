import axiosInstance from "./axios";

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
  return await axiosInstance.get<ChatMessageHitory[]>(`/chat/rooms/${roomId}`);
};

const ChatAPI = { getChatHistories };

export default ChatAPI;
