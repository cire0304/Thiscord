import {
  useState,
  useRef,
  ChangeEvent,
  KeyboardEvent,
  RefObject,
  useEffect,
} from "react";
import { styled } from "styled-components";
// import { ref as as } from "firebase/storage";
import { db } from "../../../firebase";
import {
  child,
  onChildAdded,
  onChildRemoved,
  ref,
  remove,
  set,
} from "firebase/database";
import { DmRoom } from "../../../api/roomAPI";
import { UserInfo } from "../../../api/userAPI";
import Span from "../../../components/span";
// import { ref, set, remove, push, child, serverTimestamp } from "firebase/database";

function MessageInputTextarea({
  room,
  user,
  onMessageSend,
}: {
  // TODO: room type can be GroupRoom or DmRoom later
  room: DmRoom;
  user: UserInfo;
  onMessageSend: (message: string) => void;
}) {
  const [content, setContent] = useState<string>("");
  const textareaRef = useRef<HTMLTextAreaElement>(null);
  const typingRef = ref(db, "typing");

  const [typingUsers, setTypingUsers] = useState<
    { id: string | null; name: string }[]
  >([]);

  useEffect(() => {
    if (!room.roomId) return;
    addTypeingLiteners(room.roomId);
  }, [room.roomId]);

  const addTypeingLiteners = (chatRoomId: number) => {
    let typingUsers: { id: string | null; name: string }[] = [];
    onChildAdded(child(typingRef, `${chatRoomId}`), (data) => {
      if (data.key !== user.nickname) {
        typingUsers = typingUsers.concat({
          id: data.key,
          name: data.val().userUid,
        });
        setTypingUsers(typingUsers);
      }
    });

    onChildRemoved(child(typingRef, `${chatRoomId}`), (data) => {
      const index = typingUsers.findIndex((user) => user.id === data.key);
      if (index !== -1) {
        typingUsers = typingUsers.filter((user) => user.id !== data.key);
        setTypingUsers(typingUsers);
      }
    });
  };

  const chageHandler = (e: ChangeEvent<HTMLTextAreaElement>) => {
    handleChatWriteState(e);
    handleResizeHeight(e);
  };

  const handleChatWriteState = (e: ChangeEvent<HTMLTextAreaElement>) => {
    setContent(e.target.value);
    if (e.target.value) {
      set(ref(db, `typing/${room.roomId}/${user.id}`), {
        userUid: user.nickname,
      });
    } else {
      remove(ref(db, `typing/${room.roomId}/${user.id}`));
    }
  };

  const handleResizeHeight = (e: ChangeEvent<HTMLTextAreaElement>) => {
    if (!textareaRef.current) return;
    textareaRef.current.style.height = 0 + "px";
    textareaRef.current.style.height = textareaRef.current.scrollHeight + "px";
  };

  const submitHandler = (e: KeyboardEvent<HTMLTextAreaElement>) => {
    if (e.code !== "Enter") return;
    if (!e.currentTarget.value.trim()) return;
    if (!textareaRef.current) return;

    e.preventDefault();
    onMessageSend(textareaRef.current.value);
    textareaRef.current.value = "";
    setContent("");
  };

  return (
    <Container>
      <MessageTextArea
        rows={1}
        onChange={chageHandler}
        // TODO: onKeyPress is deprecated, use onKeyDown instead lagter
        onKeyPress={submitHandler}
        placeholder={`${user.nickname}에게 메세지 보내기`}
        ref={textareaRef}
      />

      {typingUsers.length > 0 &&
        typingUsers.map((typingUser) => {
        
          // TODO: This is hard coded. It should be refactored.
          if (typingUser.id === `${user.id}`) return null;
          return (
            <TypingUser key={typingUser.id}>
              {typingUser.name}님이 채팅을 입력하고 있습니다.
            </TypingUser>
          );
        })}
    </Container>
  );
}

export default MessageInputTextarea;

const Container = styled.div`
position: relative;
`

const MessageTextArea = styled.textarea`
  padding: 5px 10px;
  width: 100%;
  height: auto;
  overflow: hidden;
  border: none;
  resize: none;
  border-radius: 5px;
  ${({ theme }) => theme.color.backgroundSecondary};
  outline: none;
  color: #bdbdbd;
`;


const TypingUser = styled(Span)`
  position: absolute;
  bottom: -13px;
  left: 10px;
  color: #bdbdbd;
  font-size: 10px;
`
  
