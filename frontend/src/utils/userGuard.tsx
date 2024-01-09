import { ReactNode, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import UserRequest, { UserInfo } from "../api/user";
import { setUserInfoState } from "../store";
import { useNavigate } from "react-router-dom";

export default function UserGuard({ children }: { children: ReactNode }) {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  useEffect(() => {
    const getUserInfo = async () => {
      const res = await UserRequest.getUserInfo();
      res.data ? dispatch(setUserInfoState(res.data)) : navigate("/login");
    };
    getUserInfo();
  }, []);

  return <>{children}</>;
}
