import { getMessaging, onMessage } from "@firebase/messaging";
import React, { useState } from "react";
import { keyframes, styled } from "styled-components";
import ProfileImage from "../profileImage";
import Span from "../span";
import { NotificationOption } from "./Notification";

function NotificationModal({
  notificationOption,
}: {
  notificationOption?: NotificationOption;
}) {
  if (!notificationOption) return null;
  const { title, body } = notificationOption;

  return (
    <Container>
      <ProfileImage
        src={`https://gravatar.com/avatar/${body.senderId}?d=identicon`}
        size="60px"
      />
      <Wrapper>
        <Title>{body.senderNickname} (DM)</Title>
        <Content>{body.content}</Content>
      </Wrapper>
    </Container>
  );
}

export default NotificationModal;

const Container = styled.div`
  padding: 15px;
  margin: 10px;
  width: 300px;
  height: 100px;

  display: flex;
  align-items: center;
  gap: 15px;
  background-color: #18191c;

  transition: all 0.3s ease-in-out;
  &:hover {
    background-color: #333639;
  }
`;

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  flex-grow: 1;
  overflow: hidden;
`;

const Title = styled(Span)`
  font-size: 20px;
  color: #fff;
  text-align: left;
`;

const Content = styled(Span)`
  font-size: 15px;
  color: #bdbdbd;

  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  width: 100%;
  text-align: left;
`;
