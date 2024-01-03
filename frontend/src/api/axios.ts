import axios, { InternalAxiosRequestConfig } from "axios";
import { SERVER_URL } from "../constants/constants";

const instance = axios.create({
  baseURL: SERVER_URL,
  withCredentials: true,
});

// todo: 개발 환경에서만 로깅하도록 수정
instance.interceptors.response.use(
  (response) => {
    console.log(response);
    return response;
  },
  (error) => {
    console.log(error.response);
    return error.response;
  }
);

export default instance;
