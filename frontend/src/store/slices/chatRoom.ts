import { createSlice } from "@reduxjs/toolkit";
import { GetRoomListResponse } from "../../api/room";

// below code is not complete yet.
// Group Room info is not included.
const initialState = {
    currentChatRoom: {
        id: 0,
    }
};

const chatRoomSlice = createSlice({
  name: "chatRoom",
  initialState,
  reducers: {
    setCurrentChatRoomId(
      state,
      action: { payload: number; type: string }
    ) {
      state.currentChatRoom.id = action.payload;
    },
  },
});

export default chatRoomSlice;
