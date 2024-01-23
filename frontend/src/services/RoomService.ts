import { createAsyncThunk } from "@reduxjs/toolkit";
import { UserInfo } from "../api/userAPI";
import axiosInstance from "../api/axios";
import { GetRoomListResponse } from "../api/roomAPI";

export const RoomService = {

    getRoomList: createAsyncThunk(
        'room/getRoomList',
        async () => {
            const response = await axiosInstance.get<GetRoomListResponse>("/rooms");
            return response.data;
        }
    ),


}
