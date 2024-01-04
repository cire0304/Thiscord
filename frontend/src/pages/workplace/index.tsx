import React from 'react'
import * as S from './styles'
import Serch from './containers/search'
import Nav from './containers/nav'
import Side from './containers/side'
import Main from './containers/main'
import Active from './containers/active'
import ProfileModal from './modals/profile/profileModal'


const WorkplacePage = () => {
  const [isProfileModalActive, setIsProfileModalActive] = React.useState<boolean>(false)

  return (
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

      {
        isProfileModalActive && <ProfileModal setIsProfileModalActive={setIsProfileModalActive} isProfileModalActive={isProfileModalActive} />
      }

    </S.Container>
  )
}

export default WorkplacePage
