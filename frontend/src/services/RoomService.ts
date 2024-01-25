import { createAsyncThunk } from "@reduxjs/toolkit";
import axiosInstance from "../api/axios";

export enum state {
    JOIN = "JOIN",
    EXIT = "EXIT",
    INVITE = "INVITE",
    REJECT = "REJECT"
}

export interface UserInfo {
    userId : number,
    email : string,
    nickname : string,
    userCode : number,
    introduction : string,
    state : state,
}

export interface DmRoom {
    roomId: number;
    otherUser : UserInfo;
}

export interface GroupRoom {
    roomId: number;
    groupName : string;
    roomUsers : UserInfo[];
}


export interface GetRoomListResponse {
    rooms: {
        dmRooms: DmRoom[],
        groupRooms: GroupRoom[],
    };
}


export const RoomService = {

    getRoomList: createAsyncThunk(
        'room/getRoomList',
        async () => {
            const response = await axiosInstance.get<GetRoomListResponse>("/rooms");
            return response.data;
        }
    ),


}
