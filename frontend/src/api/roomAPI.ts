import axiosInstance from "./axios";

// TODO: it would be better to have a type for the room in the reposnse.
export interface DmRoom {
  roomId: number;
  otherUserId: number;
  otherUserNickname: string;
  isLoading: boolean;
}

export interface GetRoomListResponse {
  rooms: DmRoom[];
}

const getRoomList = async () => {
  return await axiosInstance.get<GetRoomListResponse>("/rooms");
};

export interface RoomUser {
  userId: number;
  email: string;
  nickname: string;
  userCode: number;
  introduction: string;
  state: "JOIN" | "EXIT";
}
const getDmRoomUser = async (roomId: number, userId: number) => {
  return await axiosInstance.get<RoomUser>(`/rooms/${roomId}/users/${userId}`);
};

const createDmRoom = async (otherUserId: number) => {
  return await axiosInstance.post<DmRoom>("/rooms/dm-room", { otherUserId });
};

const exitRoom = async (roomId: number) => {
  return await axiosInstance.delete(`/rooms/${roomId}/users/me`);
};

const RoomAPI = {
  createDmRoom,
  getRoomList,
  exitRoom,
  getDmRoomUser,
};

export default RoomAPI;
