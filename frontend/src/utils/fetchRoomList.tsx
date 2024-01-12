import { ReactNode, useEffect } from "react";
import { useDispatch } from "react-redux";

import { setRoomInfoState } from "../store";
import RoomAPI from "../api/room";

export default function FetchRoomList({ children }: { children: ReactNode }) {
  const dispatch = useDispatch();

  useEffect(() => {
    const fetchRoomList = async () => {
      const res = await RoomAPI.getRoomList();

      // Is this rigth way to use redux?
      // replace with another way like socket.io
      dispatch(setRoomInfoState(res.data));
    };
    fetchRoomList();
  }, []);

  return <>{children}</>;
}
