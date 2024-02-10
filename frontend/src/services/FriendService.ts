import { createAsyncThunk } from "@reduxjs/toolkit";

import axiosInstance from "../api/axios";
import { FriendState } from "../store/slices/friendSlice";

export const FriendService = {

    getFriends: createAsyncThunk(
        'friend/getFriends',
        async () => {
            const response = await axiosInstance.get<FriendState>("/users/me/friends");
            return response.data;
        }
    ),

}
