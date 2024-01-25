import { createSlice } from "@reduxjs/toolkit";
import { GetRoomListResponse, RoomService } from "../../services/RoomService";

const initialState: GetRoomListResponse = {
  rooms: {
    dmRooms: [],
    groupRooms: [],
  }
};

const roomSlice = createSlice({
  name: "room",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(RoomService.getRoomList.fulfilled, (state, { payload }) => {
      state.rooms.dmRooms = [];
      state.rooms.groupRooms = [];

      payload.rooms.dmRooms.forEach((dmRoom) => {
        state.rooms.dmRooms.push(dmRoom);
      });

      payload.rooms.groupRooms.forEach((groupRoom) => {
        state.rooms.groupRooms.push(groupRoom);
      });
      
    });
  },
});

export default roomSlice;
