import { ReactNode } from "react";
import { useAppDispatch } from "../hooks/redux";
import { RoomService } from "../services/RoomService";

export default function FetchRoomList({ children }: { children: ReactNode }) {
  const dispatch = useAppDispatch();
  dispatch(RoomService.getRoomList());
  return <>{children}</>;
}
