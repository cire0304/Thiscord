import axiosInstance from "./axios";


const addFriend = async ({nickname, userCode}: {
    nickname: string;
    userCode: string;
}) => {
  return await axiosInstance.post("users/me/friends", { nickname, userCode });
}

export interface GetFriendResponse {
    friends : {
        id : number;
        senderId : number;
        senderNickname : string;
        senderUserCode : number;
        receiverId : number;
        receiverNickname : string;
        receiverUserCode : number;
        state : string;
    }[]
}
const getFriendList = async () => {
    return await axiosInstance.get<GetFriendResponse>("/users/me/friends");
}

const acceptFriend = async (id: number) => {
    return await axiosInstance.put(`/users/me/friends`, { id , state : "ACCEPT" });
}

const rejectFriend = async (id: number) => {
    return await axiosInstance.put(`/users/me/friends`, { id , state : "REJECT" });
}

const FriendReqeust = {
    addFriend,
    getFriendList,
    acceptFriend,
    rejectFriend
}

export default FriendReqeust;