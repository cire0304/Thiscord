import NotificationModal from "./NotificationModal";
import { getMessaging, onMessage } from "@firebase/messaging";
import { useNavigate, useParams } from "react-router";
import styled, { keyframes } from "styled-components";
import { useState } from "react";
import { Notify } from "../../utils/Alarm";
import { setCurrentChatRoomId, setRoomInfoState } from "../../store";
import RoomAPI, { DmRoom, GetRoomListResponse } from "../../api/roomAPI";
import { useDispatch, useSelector } from "react-redux";

enum NotificationType {
  Message = "message",
  Notify = "notify",
}

export interface NotificationOption {
  title: string;
  // if type is message, message is NotificationMessage
  // if type is notify, message is another type
  // TODO: if type is room, message is another type
  body: MessageBody;
  type?: NotificationType;
  onClick?: () => void;
}

export interface MessageBody {
  roomId: number;
  senderId: number;
  senderNickname: string;
  content: string;
}

export default function Notification({
  children,
}: {
  children: React.ReactNode;
}) {
  const [isActivated, setIsActivated] = useState(false);
  const [notificationOption, setNotificationOption] =
    useState<NotificationOption>();

  const roomList = useSelector((state: any) => state.room) as GetRoomListResponse;

  const roomId = useParams();
  const navigate = useNavigate();
  
  const dispatch =  useDispatch();
  const messaging = getMessaging();
  onMessage(messaging, (payload) => {
    if (
      !payload.notification ||
      !payload.notification.title ||
      !payload.notification.body
    ) {
      alert("payload.notification is null");
      return;
    }

    const { title, body } = payload.notification;
    const messageBody = JSON.parse(body) as MessageBody;
    if (!title || !messageBody) {
      return;
    }

    if (roomId && roomId.roomId === messageBody.roomId.toString()) {
      return;
    }

    if (!roomList?.rooms.find((room) => room.roomId === messageBody.roomId)) {
      const fetchRoomList = async () => {
        const res = await RoomAPI.getRoomList();
        dispatch(setRoomInfoState(res.data));
      };
      fetchRoomList();
    }

    setNotificationOption({
      ...notificationOption,
      title: title,
      body: messageBody,
    });
    setIsActivated(true);
    Notify.play();
  });


  const changeChatRoom = (room: DmRoom) => {
    dispatch(setCurrentChatRoomId(room));
    navigate(`/workspace/rooms/${room.roomId}`);
  };
  return (
    <>
      {children}
      {
        <Container
          $isActived={isActivated}
          onClick={() => {
            if (!notificationOption?.body) return;
            setIsActivated(false);
            changeChatRoom({
              roomId: notificationOption.body.roomId,
              otherUserId: notificationOption.body.senderId,
              otherUserNickname: notificationOption.body.senderNickname,
              isLoading: true,
            });
          }}
        >
          <NotificationModal notificationOption={notificationOption} />
          <CancleButton onClick={() => setIsActivated(false)}>X</CancleButton>
        </Container>
      }
    </>
  );
}

const show = keyframes`
  0% {
    transform:translate(0px,0px);
  }
  100%{
    transform:translate(-500px,0px);
  }
`;

const remove = keyframes`
  0% {
    transform:translate(-500px,0px);
  }
  100%{
    transform:translate(0px,0px);
  }
`;

const Container = styled.div<{ $isActived?: boolean }>`
  position: fixed;
  bottom: 0;
  right: -500px;
  animation: ${(props) => (props.$isActived === true ? show : remove)} 0.3s
    linear forwards;
`;

const CancleButton = styled.button`
  position: absolute;
  top: 15px;
  right: 15px;
  width: 30px;
  height: 30px;

  border: none;
  background-color: transparent;
  color: #fff;
  font-size: 16px;

  &:hover {
    cursor: pointer;
    color: #bdbdbd;
  }
`;
