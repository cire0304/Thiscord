import { useAppSelector } from "../../../hooks/redux";
import { GroupRoom } from "../../../services/RoomService";

export default function GroupChatHeader( {room}: {room?:GroupRoom}) {
  if (!room) return (<></>);  

  return (
    <></>
  );
}

