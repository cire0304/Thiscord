import React from "react";
import styled from "styled-components";
import { ReactComponent as GearIcon } from "../../../../../assets/icons/gear.svg";
import { ReactComponent as HeadphoneIcon } from "../../../../../assets/icons/headphone.svg";
import { ReactComponent as MuteMike } from "../../../../../assets/icons/muteMike.svg";
import { ReactComponent as ActiveMike } from "../../../../../assets/icons/activeMike.svg";

import Profile from "../../../../../assets/images/discodeProfile.jpg";

import Span from "../../../../../components/span";
import theme from "../../../../../styles/theme";
import ProfileImage from "./profileImage";


const Container = styled.div`
  width: 100%;
  height: 50px;
  padding: 5px 10px;

  ${({ theme }) => theme.flex.rowCenterCenter};
  ${({ theme }) => theme.color.backgroundSecondary};
`;

const UserInfoWrapper = styled.div`
  ${({ theme }) => theme.flex.columnCenterStart};
  flex-grow: 1;
`;

const ControleWrapper = styled.div`
  gap: 10px;
  ${({ theme }) => theme.flex.rowCenterCenter};
`;



const ControlBar = ({nickname, userCode} : {nickname: string, userCode: number}) => {
  return (
    <Container>
      <ProfileImage src={Profile} />
      <UserInfoWrapper>
        <Span styles={[theme.color.neutral]}>{nickname}</Span>
        <Span styles={[theme.color.secondary, theme.fontFormat.footnote]}>
          {nickname}#{userCode}
        </Span>
      </UserInfoWrapper>
      <ControleWrapper>
        <MuteMike></MuteMike>
        <HeadphoneIcon> </HeadphoneIcon>
        <GearIcon></GearIcon>
      </ControleWrapper>
    </Container>
  );
};

export default ControlBar;
