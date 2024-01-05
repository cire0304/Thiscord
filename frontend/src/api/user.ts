import { get } from "http";
import axiosInstance from "./axios";

const registerUser = async (data: {
  email: string;
  password: string;
  nickname: string;
}) => {
  return await axiosInstance.post("/register", data);
};

const login = async (email: string, password: string) => {
  const data = {
    email,
    password,
  };
  return await axiosInstance.post("/login", data);
};

const getUserInfo = async () => {
  return await axiosInstance.get<{
    id: number;
    email: string;
    nickname: string;
    userCode: string;
  }>("/users/me");
};

const getUserDetailInfo = async () => {
  return await axiosInstance.get<{
    id: number;
    email: string;
    nickname: string;
    userCode: string;
    introduction: string;
    createdAt: string;
  }>("/users/me/detail");
};

const updateUserInfo = async (nickname: string, introduction: string) => {
  const data = {
    nickname,
    introduction,
  };
  return await axiosInstance.put("/users/me", data);
};

const UserRequest = {
  registerUser,
  login,
  getUserInfo,
  getUserDetailInfo,
  updateUserInfo
};

export default UserRequest;
