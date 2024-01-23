import { createSlice } from "@reduxjs/toolkit";
import { GetRoomListResponse } from "../../api/roomAPI";
import { RoomService } from "../../services/RoomService";

// below code is not complete yet.
// Group Room info is not included.
const initialState: GetRoomListResponse = {
  rooms: [],
};

const roomSlice = createSlice({
  name: "room",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(RoomService.getRoomList.fulfilled, (state, { payload }) => {
      state.rooms = [];
      payload.rooms.forEach((dmRoom) => {
        state.rooms.push(dmRoom);
      });
    });
  },
});

export default roomSlice;
