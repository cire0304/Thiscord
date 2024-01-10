import { configureStore } from "@reduxjs/toolkit";
import userSlice from "./slices/user";
import viewStateSlice from "./slices/viewState";
import roomSlice from "./slices/rooms";

export default configureStore({
  reducer: {
    user: userSlice.reducer,
    viewState: viewStateSlice.reducer,
    room: roomSlice.reducer,
  },
});

export const { setUserInfoState } = userSlice.actions;
export const { activeById } = viewStateSlice.actions;
export const { setRoomInfoState } = roomSlice.actions;
