import { createSlice } from "@reduxjs/toolkit";
import { GetRoomListResponse } from "../../api/roomAPI";

// below code is not complete yet.
// Group Room info is not included.
const initialState: GetRoomListResponse = {
  dmRooms: [],
};

const roomSlice = createSlice({
  name: "room",
  initialState,
  reducers: {
    setRoomInfoState(
      state,
      action: { payload: GetRoomListResponse; type: string }
    ) {
      state.dmRooms = [];

      action.payload.dmRooms.forEach((dmRoom) => {
        state.dmRooms.push(dmRoom);
      });
    },
  },
});

export default roomSlice;
