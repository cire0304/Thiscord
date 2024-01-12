import { useState, useRef, ChangeEvent, KeyboardEvent } from "react";
import { styled } from "styled-components";

function MessageInputTextarea({ nickname }: { nickname?: string }) {
  const textareaRef = useRef<HTMLTextAreaElement>(null);

  const handleResizeHeight = (e: ChangeEvent<HTMLTextAreaElement>) => {
    if (!textareaRef.current) return;

    textareaRef.current.style.height = 0 + "px";
    textareaRef.current.style.height = textareaRef.current.scrollHeight + "px";
  };

  const handleSubmit = (e: KeyboardEvent<HTMLTextAreaElement>) => {
    if (e.key !== "Enter") return;
    e.preventDefault();
    if (textareaRef.current) {
      // 매세지 보내는 로직 작성
      alert(e.currentTarget.value);
      textareaRef.current.value = "";
    }
  };

  return (
    <>
      <MessageTextArea
        rows={1}
        onChange={(e) => handleResizeHeight(e)}
        onKeyDown={(e) => handleSubmit(e)}
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
