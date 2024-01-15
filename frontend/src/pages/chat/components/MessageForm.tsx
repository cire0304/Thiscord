import { useState, useRef, ChangeEvent, KeyboardEvent, RefObject } from "react";
import { styled } from "styled-components";

function MessageInputTextarea({ nickname, onMessageSend }: { nickname?: string, onMessageSend: (message: string) => void }) {
  const textareaRef = useRef<HTMLTextAreaElement>(null);

  const handleResizeHeight = (e: ChangeEvent<HTMLTextAreaElement>) => {
    if (!textareaRef.current) return;

    textareaRef.current.style.height = 0 + "px";
    textareaRef.current.style.height = textareaRef.current.scrollHeight + "px";
  };

  const handleSubmit = (e: KeyboardEvent<HTMLTextAreaElement>) => {
    if (e.code !== "Enter") return;
    if (!e.currentTarget.value.trim()) return;
    if (textareaRef.current) {
      e.preventDefault();
      onMessageSend(textareaRef.current.value);
      textareaRef.current.value = "";
    }
  };

  return (
    <>
      <MessageTextArea
        rows={1}
        onChange={(e) => handleResizeHeight(e)}
        onKeyPress={handleSubmit}
        placeholder={`${nickname}에게 메세지 보내기`}
        ref={textareaRef}
      />
    </>
  );
}

export default MessageInputTextarea;

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
