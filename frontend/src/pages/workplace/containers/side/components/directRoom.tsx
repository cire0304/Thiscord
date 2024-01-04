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

const DirectRoom = ({ nickname }: { nickname: string }) => {
  return (
    <Container>
      <ProfileImage src={Profile} />
      <InfoWrapper>
        <Span styles={[theme.color.secondary]}>{nickname}님과의 대화</Span>
      </InfoWrapper>
    </Container>
  );
};

export default DirectRoom;
