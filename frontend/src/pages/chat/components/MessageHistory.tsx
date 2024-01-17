import { styled } from "styled-components";
import { ChatMessageHitory } from "../../../api/ChatAPI";
import ProfileImage from "../../../components/profileImage";
import Span from "../../../components/span";
import { convertDateFormat } from "../../../utils/Dates";
import { useEffect, useRef } from "react";


function MessageHistory({
  chatHistories,
}: {
  chatHistories?: ChatMessageHitory[];
}) {

  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  });

  const messagesEndRef = useRef<HTMLDivElement>(null);
  return (
    <div>
      {chatHistories &&
        chatHistories.map((chat, index) => (
          <div
            style={{
              display: "flex",
              alignItems: "center",
              padding: "10px",
              width: "100%",
              boxSizing: "border-box",
            }}
            key={index}
          >
            <ProfileImage
              src={`https://gravatar.com/avatar/${chat.user.id}?d=identicon`}
              size="30px"
            />
            <div
              style={{
                display: "flex",
                flexDirection: "column",
                justifyContent: "flex-start",
                alignItems: "flex-start",
                padding: "0 10px",
                width: "100%",
                boxSizing: "border-box",
              }}
            >
              <OuterWrapper>
                <Wrapper>
                  <Nickname>{chat.user.nickname}</Nickname>
                  <Time>{convertDateFormat(chat.message.sentDateTime)}</Time>
                </Wrapper>
                <ChatMessage> {chat.message.content}</ChatMessage>
              </OuterWrapper>
            </div>
          </div>
        ))}

        <div ref={messagesEndRef}></div>
    </div>
  );
}

export default MessageHistory;

const OuterWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 5px;
  width: 100%;
`;

const Wrapper = styled.div`
  display: flex;
  /* justify-content:  */
  align-items: flex-end;
  gap: 5px;
`;

const Nickname = styled(Span)`
  font-size: 14px;
  color: #fff;
  -webkit-user-select: text;
  -moz-user-select: text;
  -ms-user-select: text;
  user-select: text;
`;

const Time = styled(Span)`
  font-size: 10px;
  color: #585555;
  -webkit-user-select: text;
  -moz-user-select: text;
  -ms-user-select: text;
  user-select: text;
`;

const ChatMessage = styled(Span)`
  font-size: 14px;
  color: #fff;
  -webkit-user-select: text;
  -moz-user-select: text;
  -ms-user-select: text;
  user-select: text;
`;
