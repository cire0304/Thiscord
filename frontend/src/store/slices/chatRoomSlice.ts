import { createSlice } from "@reduxjs/toolkit";
import { DmRoom, GroupRoom } from "../../services/RoomService";

export enum Roomtype {
  DM = "DM",
  GROUP = "GROUP",
  LODING = "LODING",
}

export interface ChatRoomState {
  currentRoomId: number;
  currentRoomType: Roomtype;
  currentDmChatRoom?: DmRoom;
  currentGroupChatRoom?: GroupRoom;
}

const initialState: ChatRoomState = {
  currentRoomId: 0,
  currentRoomType: Roomtype.LODING,
};
const chatRoomSlice = createSlice({
  name: "chatRoom",
  initialState,
  reducers: {

    setCurrentChatRoom(state, { payload }) {
      const { room, roomType } = payload;

      state.currentRoomId = room.roomId;
      if (roomType === Roomtype.GROUP) {
        state.currentRoomType = Roomtype.GROUP;
        state.currentGroupChatRoom = { ...room };
      } else {
        state.currentRoomType = Roomtype.DM;
        state.currentDmChatRoom = { ...room };
      }
    },

    setCurrentDmChatRoom(state, { payload }) {
      state.currentRoomId = payload.roomId;
      state.currentRoomType = Roomtype.DM;
      state.currentDmChatRoom = { ...payload };
    },
    setCurrentGroupChatRoom(state, { payload }) {
      state.currentRoomId = payload.roomId;
      state.currentRoomType = Roomtype.GROUP;
      state.currentGroupChatRoom = { ...payload };
    },
  },
});

export default chatRoomSlice;
