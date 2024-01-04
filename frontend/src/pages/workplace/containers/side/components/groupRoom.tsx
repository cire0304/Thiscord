import React from "react";
import { styled } from "styled-components";
import Profile from "../../../../../assets/images/discodeProfile.jpg";
import ProfileImage from "./profileImage";
import Span from "../../../../../components/span";
import theme from "../../../../../styles/theme";

const Container = styled.div`
  width: 100%;
  height: 50px;
  padding: 5px 10px;

  ${({ theme }) => theme.flex.rowStartCenter};
`;

const InfoWrapper = styled.div`
  ${({ theme }) => theme.flex.columnCenterStart};
  flex-grow: 1;
`;

const GroupRoom = ({
  nickname,
  userCount,
}: {
  nickname: string;
  userCount: number;
}) => {
  return (
    <Container>
      <ProfileImage src={Profile} />
      <InfoWrapper>
      <Span styles={[theme.color.secondary]}>{nickname}님의 그룹</Span>
      <Span styles={[theme.color.secondary, theme.fontFormat.footnote ]}>맴버 {userCount}명</Span>
      </InfoWrapper>
    </Container>
  );
};

export default GroupRoom;
