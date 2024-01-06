import { configureStore } from "@reduxjs/toolkit";
import userSlice from "./slices/user";
import viewStateSlice from "./slices/viewState";

export default configureStore({
  reducer: {
    user: userSlice.reducer,
    viewState: viewStateSlice.reducer,
  },
});

export const { setUserInfoState } = userSlice.actions;
export const { activeById } = viewStateSlice.actions;
