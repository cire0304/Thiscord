import { combineReducers, configureStore } from "@reduxjs/toolkit";
import userSlice from "./slices/userSlice";
import viewStateSlice from "./slices/viewStateSlice";
import roomSlice from "./slices/roomsSlice";
import chatRoomSlice from "./slices/chatRoomSlice";
import friendSlice from "./slices/friendSlice";

import { persistReducer, persistStore } from "redux-persist";
import storage from "redux-persist/lib/storage";


const persistConfig = {
  key: 'root',
  storage,
  whitelist: ['user'],
}

const reducer = combineReducers({
  user: userSlice.reducer,
  friend: friendSlice.reducer,
  viewState: viewStateSlice.reducer,
  room: roomSlice.reducer,
  chatRoom: chatRoomSlice.reducer,
})

const persistedReducer = persistReducer(persistConfig, reducer) 

export const store = configureStore({
  reducer: persistedReducer,
  middleware: (getDefaultMiddleware) => getDefaultMiddleware({serializableCheck: false}),
});

export default store;
export const persistor = persistStore(store);

export const { activeById } = viewStateSlice.actions;
export const { setCurrentDmChatRoom, setCurrentGroupChatRoom, setCurrentChatRoom } = chatRoomSlice.actions;

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch

