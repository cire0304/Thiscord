import axiosInstance from "./axios";

const sendEmailCode = async (email: string) => {
  return await axiosInstance.post("/send-email", { email });
};

const checkEmailCode = async (emailCode: string) => {
  return await axiosInstance.post("/check-email", { emailCode });
};

const AuthAPI = {
  checkEmailCode,
  sendEmailCode
};

export default AuthAPI;
