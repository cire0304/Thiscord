import { createAsyncThunk } from "@reduxjs/toolkit";
import { UserInfo } from "../api/userAPI";
import axiosInstance from "../api/axios";

export const UserService = {

    getMyInfo: createAsyncThunk(
        'user/getMyInfo',
        async () => {
            const response = await axiosInstance.get<UserInfo>("/users/me");
            return response.data;
        }
    ),

    updateMyInfo: createAsyncThunk(
        'user/updateMyInfo',
        async (data: { nickname: string, introduction: string }) => {
            const response = await axiosInstance.put<UserInfo>("/users/me", data);
            return response.data;
        }
    )

}
