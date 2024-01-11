import React, { Dispatch, SetStateAction, useEffect, useRef } from "react";
import styled from "styled-components";
import { ReactComponent as GearIcon } from "../../../assets/icons/gear.svg";
import { ReactComponent as HeadphoneIcon } from "../../../assets/icons/headphone.svg";
import { ReactComponent as MuteMike } from "../../../assets/icons/muteMike.svg";
import { ReactComponent as ActiveMike } from "../../../assets/icons/activeMike.svg";

import Profile from "../../../assets/images/discodeProfile.jpg";
import Span from "../../span";
import { useDispatch, useSelector } from "react-redux";
import UserRequest from "../../../api/user";
import { setUserInfoState } from "../../../store";
import theme from "../../../styles/theme";
import ProfileImage from "./profileImage";

export default function ControlBar({
  setIsProfileModalActive,
}: {
  setIsProfileModalActive: Dispatch<SetStateAction<boolean>>;
}) {
  const userInfoWrapperRef = useRef<HTMLDivElement>(null);
  const clickHandler = (e: React.MouseEvent<HTMLDivElement>) => {
    setIsProfileModalActive(true);
  };

  const dispatch = useDispatch();

  useEffect(() => {
    const getUserInfo = async () => {
      const res = await UserRequest.getUserInfo();
      res.data && dispatch(setUserInfoState(res.data));
    };
    getUserInfo();
  }, []);

  const user = useSelector((state: any) => state.user);

  return (
    <Container>
      <ProfileImage src={Profile} />
      <UserInfoWrapper
        onClick={(e) => clickHandler(e)}
        ref={userInfoWrapperRef}
      >
        <UserNickname styles={[theme.color.neutral]}>
          {user.nickname}
        </UserNickname>
        <UserNickname
          styles={[theme.color.secondary, theme.fontFormat.footnote]}
        >
          {user.nickname}#{user.userCode}
        </UserNickname>
      </UserInfoWrapper>
      <ControleWrapper>
        <MuteMike></MuteMike>
        <HeadphoneIcon> </HeadphoneIcon>
        <GearIcon></GearIcon>
      </ControleWrapper>
    </Container>
  );
}

const Container = styled.div`
  max-width: 250px;
  width: 100%;
  height: 50px;
  padding: 5px 10px;

  ${({ theme }) => theme.flex.rowCenterCenter};
  ${({ theme }) => theme.color.backgroundSecondary};
`;

const UserInfoWrapper = styled.div`
  ${({ theme }) => theme.flex.columnCenterStart};
  max-width: 100px;
  flex-grow: 1;

  margin: 5px;
  border-radius: 5px;

  &:hover {
    ${({ theme }) => theme.color.backgroundPrimary};
  }
`;

const UserNickname = styled(Span)`
  width: 100%;
  display: block;
  white-space: nowrap;
  text-overflow: ellipsis;
  overflow: hidden;
  padding-left: 5px;
  text-align: left;
`;
const ControleWrapper = styled.div`
  gap: 10px;
  ${({ theme }) => theme.flex.rowCenterCenter};
`;
