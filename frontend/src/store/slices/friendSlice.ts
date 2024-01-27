import { createSlice } from '@reduxjs/toolkit';
import { FriendService } from '../../services/FriendService';

export interface FriendDTO {
    id: number,
    userId: number;
    email: string;
    nickname: string;
    userCode: number;
}

export interface FriendState {
    friends: FriendDTO[]
};

const initialState: FriendState = {
    friends: []
}

const friendSlice = createSlice({
    name: 'friend',
    initialState,
    reducers: {
        clearFriend() {
            return initialState
        }
    },
    extraReducers: (builder) => {
        builder
            .addCase(FriendService.getFriends.fulfilled, (state, { payload }) => {  
                state.friends = payload.friends;
            })
        }
    });

export default friendSlice;
