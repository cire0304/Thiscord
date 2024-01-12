import axiosInstance from "./axios";

// it would be better to have a type for the room in the reposnse.
export interface DmRoom {
  roomId: number;
  otherUserId: number;
  otherUserNickname: string;
}

export interface GetRoomListResponse {
  dmRooms: DmRoom[];
}

const getRoomList = async () => {
  return await axiosInstance.get<GetRoomListResponse>("/rooms");
};

const createDmRoom = async (otherUserId: number) => {
  return await axiosInstance.post("/rooms/dm-room", { otherUserId });
};

const exitRoom = async (roomId: number) => {
  return await axiosInstance.delete(`/rooms/dm-room/${roomId}/users/me`);
};

const RoomAPI = {
  createDmRoom,
  getRoomList,
  exitRoom,
};

export default RoomAPI;
