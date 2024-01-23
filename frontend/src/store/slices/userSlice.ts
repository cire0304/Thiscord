import { createSlice } from "@reduxjs/toolkit";
import { UserInfo } from "../../api/userAPI";
import { UserService } from "../../services/UserService";

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
    clearUser() {
      
    }
  },
  extraReducers: (builder) => {
    builder
      .addCase(UserService.getMyInfo.fulfilled, (state, {payload}) => {
        return payload;
      })
      .addCase(UserService.updateMyInfo.fulfilled, (state, { payload }) => {
        return payload
      })
  }
});

export default userSlice;
