import React, { Dispatch, SetStateAction, useRef } from "react";
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
  padding-left: 5px;
  margin-left: 5px;
  border-radius: 5px;
  
  &:hover {
    ${({ theme }) => theme.color.backgroundPrimary};
  }
`;

const ControleWrapper = styled.div`
  gap: 10px;
  ${({ theme }) => theme.flex.rowCenterCenter};
`;

const ControlBar = ({
  nickname,
  userCode,
  setIsProfileModalActive,
}: {
  nickname: string;
  userCode: number;
  setIsProfileModalActive: Dispatch<SetStateAction<boolean>>;
}) => {
  const userInfoWrapperRef = useRef<HTMLDivElement>(null);
  const clickHandler = (e: React.MouseEvent<HTMLDivElement>) => {
    setIsProfileModalActive(true);
  };

  return (
    <Container>
      <ProfileImage src={Profile} />
      <UserInfoWrapper
        onClick={(e) => clickHandler(e)}
        ref={userInfoWrapperRef}
      >
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
