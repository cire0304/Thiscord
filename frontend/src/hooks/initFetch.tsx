import { ReactNode, useEffect } from "react";
import { useAppDispatch } from "./redux";
import { FriendService } from "../services/FriendService";

function InitFetch({ children }: { children: ReactNode }) {
  const dispatch = useAppDispatch();
  useEffect(() => {
    dispatch(FriendService.getFriends());
  }, []);

  // TODO: init another fetch

  return <>{children}</>;
}

export default InitFetch;
