import { configureStore } from "@reduxjs/toolkit";
import userSlice from "./slices/userSlice";
import viewStateSlice from "./slices/viewStateSlice";
import roomSlice from "./slices/roomsSlice";
import chatRoomSlice from "./slices/chatRoomSlice";

export const store = configureStore({
  reducer: {
    user: userSlice.reducer,
    viewState: viewStateSlice.reducer,
    room: roomSlice.reducer,
    chatRoom: chatRoomSlice.reducer,
  },
});

export default store;

export const { activeById } = viewStateSlice.actions;
export const { setCurrentChatRoomId } = chatRoomSlice.actions;

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch
