import axiosInstance from "./axios";

interface Data {
  email: string;
  password: string;
  nickname: string;
}

const registerUser = async (data: {
  email: string;
  password: string;
  nickname: string;
}) => {
    return await axiosInstance.post("/register", data);
};

const UserRequest = {
  registerUser,
};

export default UserRequest;
