import { useState } from "react";
import { ReactComponent as GroupAddIcon } from "../../../assets/icons/addGroup.svg";
import { styled } from "styled-components";
import Tooltip from "../../../components/Tooltip";
import GroupAddModal from "../modal/groupAddModal";
export default function GroupButton() {
  const [isModalActive, setIsModalActive] = useState(false);

  return (
    <Container>
      <Tooltip message="그룹 추가하기">
        <Button onClick={() => setIsModalActive(!isModalActive)}></Button>
      </Tooltip>

      {isModalActive && <GroupAddModal></GroupAddModal>}
    </Container>
  );
}

const Container = styled.div`
  position: absolute;
  z-index: 100;
  right: 10px;
  top: 50%;
  transform: translateY(-50%);
`;

const Button = styled(GroupAddIcon)`
  width: 25px;
  height: 25px;

  &:hover {
    cursor: pointer;
    fill: #b5bac1;
  }
`;
