import { createSlice } from "@reduxjs/toolkit";

export interface ViewState {
    infos: {
        id: number;
        name: string;
        active: boolean;
    }[];
}
const initialState: ViewState = {
    infos: [
        {
            id: 0,
            name: "온라인",
            active: true,
        },
        {
            id: 1,
            name: "모두",
            active: false,
        },
        {
            id: 2,
            name: "대기중",
            active: false,
        },
        {
            id: 3,
            name: "차단 목록",
            active: false,
        },
        {
            id: 4,
            name: "친구 추가하기",
            active: false,
        },
    ],
};

const viewStateSlice = createSlice({
  name: "viewState",
  initialState,
  reducers: {
    activeById(state, action: { payload: {id: number}; type: string }) {
        const id = action.payload.id;
        state.infos.map((item) => {
            if (item.id === id) {
                item.active = true;
            } else {
                item.active = false;
            }
        });
    },
  },
});

export default viewStateSlice;
