import { useState } from "react";
import { ReactComponent as GroupAddIcon } from "../../../assets/icons/addGroup.svg";
import { styled } from 'styled-components';
import Tooltip from "../../../components/Tooltip";
export default function GroupButton() {

  const [isButtonHover, setIsButtonHover] = useState(false);

  
  return (
    <Container>

      <Tooltip message="hi">
      <Button 
      onMouseOver={() => setIsButtonHover(true)}
      onMouseLeave={() => setIsButtonHover(false)}  >groupButton</Button>
      </Tooltip>

    </Container> 
  )
}

const Container = styled.div`
  position: absolute;
  right: 10px;
  top: 50%;
  transform: translateY(-50%);
`;

const Button = styled(GroupAddIcon)`
  width: 25px;
  height: 25px;

  
  &:hover {
    cursor: pointer;
    fill: #B5BAC1;
  }
`
