import axiosInstance from "./axios";

const addFriend = async ({
  nickname,
  userCode,
}: {
  nickname: string;
  userCode: string;
}) => {
  return await axiosInstance.post("users/me/friends", { nickname, userCode });
};

export interface FriendInfo {
    id: number;
    userId: number;
    email: string;
    nickname: string;
    userCode: number;
}

export interface GetFriendResponse {
  friends: FriendInfo[];
}
const getFriendList = async () => {
  return await axiosInstance.get<GetFriendResponse>("/users/me/friends");
};

export interface GetFriendRequestResponse {
    receivedFriendRequests: FriendInfo[],
    sentFriendRequests: FriendInfo[]
  }

const getFriendRequestList = async () => {
    return await axiosInstance.get<GetFriendRequestResponse>("/users/me/friend-requests");
}

const acceptFriend = async (id: number) => {
  return await axiosInstance.put(`/users/me/friends`, { id, state: "ACCEPT" });
};

const rejectFriend = async (id: number) => {
  return await axiosInstance.put(`/users/me/friends`, { id, state: "REJECT" });
};

const FriendAPI = {
  addFriend,
  getFriendList,
  getFriendRequestList,
  acceptFriend,
  rejectFriend,
};

export default FriendAPI;
