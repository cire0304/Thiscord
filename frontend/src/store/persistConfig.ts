import { combineReducers } from "@reduxjs/toolkit";
import userSlice from "./slices/userSlice";
import { persistReducer } from "redux-persist";

const persistConfig = {
  key: "root",
  storage: localStorage,
  whitelist: ["user"],
};

const reducer = combineReducers({
  user: userSlice.reducer,
});

export default persistReducer(persistConfig, reducer);
