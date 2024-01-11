import React, { useEffect } from "react";
import * as S from "./styles";
import Serch from "./containers/search";
import Nav from "./containers/nav";
import Side from "./containers/side";
import Main from "./containers/main";
import Active from "./containers/active";
import ProfileModal from "./modals/profile/profileModal";
import UserGuard from "../../utils/userGuard";
import FetchRoomList from "../../utils/fetchRoomList";

const WorkplacePage = () => {
  const [isProfileModalActive, setIsProfileModalActive] =
    React.useState<boolean>(false);
  return (
    <UserGuard>
      <FetchRoomList>
        <S.Container>
          <S.Header>
            <Serch></Serch>
            <Nav></Nav>
          </S.Header>

          <S.Body>
            <Side setIsProfileModalActive={setIsProfileModalActive}></Side>
            <Main></Main>
            <Active></Active>
          </S.Body>

          {isProfileModalActive && (
            <ProfileModal
              setIsProfileModalActive={setIsProfileModalActive}
              isProfileModalActive={isProfileModalActive}
            />
          )}
        </S.Container>
      </FetchRoomList>
    </UserGuard>
  );
};

export default WorkplacePage;
