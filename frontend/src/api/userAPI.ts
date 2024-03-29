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

export interface UserInfo {
  id: number;
  nickname: string;
  userCode: number;
  email: string;
  introduction: string;
}

const getUserInfo = async () => {
  return await axiosInstance.get<UserInfo>("/users/me");
};

const updateUserInfo = async (nickname: string, introduction: string) => {
  const data = {
    nickname,
    introduction,
  };
  return await axiosInstance.put("/users/me", data);
};

const UserAPI = {
  registerUser,
  login,
  getUserInfo,
  updateUserInfo
};

export default UserAPI;
