import { createSlice } from "@reduxjs/toolkit";
import { DmRoom, GetRoomListResponse } from "../../api/roomAPI";

interface ChatRoomState {
  currentChatRoom: DmRoom;
}

// TODO:  below code is not complete yet.
// Group Room info is not included.
const initialState:ChatRoomState = {
    currentChatRoom: {
        roomId: 0,
        otherUserId: 0,
        otherUserNickname: "",
        isLoading: false,
    }
};

const chatRoomSlice = createSlice({
  name: "chatRoom",
  initialState,
  reducers: {
    setCurrentChatRoomId(
      state,
      action: { payload: DmRoom; type: string }
    ) {
      state.currentChatRoom = {...action.payload, isLoading: true};
    },
  },
});

export default chatRoomSlice;
