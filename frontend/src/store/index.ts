import {
  configureStore,
  createAsyncThunk,
  createSlice,
} from "@reduxjs/toolkit";
import { UserInfo } from "../api/user";
import { stat } from "fs";

const initialState: UserInfo = {
  id: 0,
  nickname: "로딩 중",
  userCode: 0,
  email: "로딩 중",
  introduction: "",
};

const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {
    setUserInfoState(state, action: { payload: UserInfo; type: string }) {
      return action.payload;
    },
  },
});

export default configureStore({
  reducer: {
    user: userSlice.reducer,
  },
});

export const { setUserInfoState } = userSlice.actions;
