import React from 'react'
import * as S from './styles'
import Serch from './containers/search'
import Nav from './containers/nav'
import Side from './containers/side'
import Main from './containers/main'
import Active from './containers/active'


const WorkplacePage = () => {
  return (
    <S.Container>
      <S.Header>
        <Serch></Serch>
        <Nav></Nav>
      </S.Header>

      <S.Body>
        <Side></Side>
        <Main></Main>
        <Active></Active>
      </S.Body>
    </S.Container>
  )
}

export default WorkplacePage
