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

const UserRequest = {
  registerUser,
  login,
};

export default UserRequest;
