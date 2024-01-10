import { createSlice } from "@reduxjs/toolkit";
import { UserInfo } from "../../api/user";
import { GetRoomListResponse, RoomDmInfo } from "../../api/room";

// below code is not complete yet.
// Group Room info is not included.
const initialState: GetRoomListResponse = {
  roomDmInfos: [],
};

const roomSlice = createSlice({
  name: "room",
  initialState,
  reducers: {
    setRoomInfoState(
      state,
      action: { payload: GetRoomListResponse; type: string }
    ) {
      state.roomDmInfos = [];

      action.payload.roomDmInfos.forEach((roomDmInfo) => {
        state.roomDmInfos.push(roomDmInfo);
      });
    },
  },
});

export default roomSlice;
