import { styled } from "styled-components";
import Span from "../../../components/span";
import Button from "../../../components/button";
import { useAppDispatch, useAppSelector } from "../../../hooks/redux";
import FriendBar from "./friendBar";
import { useState } from "react";
import CheckboxGroup from "../../../components/checkBox/checkboxGroup";
import { RoomService, CreateGroupRoomRequest } from "../../../services/RoomService";

export default function GroupAddModal() {
  const [freindIds, setFriendIds] = useState<string[]>([]); 

  const dispatch = useAppDispatch();
  const friends = useAppSelector((state) => state.friend.friends);

  const onSubmit = () => {
    const req: CreateGroupRoomRequest = {
      otherUserIds: freindIds as any[],
      groupName: "그룹", 
    }
    dispatch(RoomService.createGroupRoom(req));
  }
  
  return (
    <Container>
      <Title>친구 선택하기</Title>
      <Header>{freindIds
      .map((id) => friends.findIndex(friend => friend.userId.toString() === id))
      .map((index) => friends[index].nickname)
      .join(" , ")}</Header>
      <Content>
        <CheckboxGroup
          values={freindIds}
          onChange={setFriendIds}
        >
          {friends.map((friend) => {
            return <FriendBar friend={friend}></FriendBar>;
          })}
        </CheckboxGroup>
      </Content>
      
      <SubmitButton onClick={onSubmit}>그룹 생성</SubmitButton>
    </Container>
  );
}

const Container = styled.div`
  width: 440px;
  height: 400px;
  margin-top: 10px;
  position: absolute;
  transform: translateX(-410px);

  background-color: #313338;
  border-radius: 3px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.5);

  display: flex;
  flex-direction: column;
  align-items: center;

  padding: 20px;
`;

const Header = styled.header`
  display: flex;
  height: 40px;
  width: 100%;

  padding: 5px 10px;
  ${({ theme }) => theme.color.backgroundSecondary}
  ${({ theme }) => theme.color.neutral}
    flex-grow: 1;

  border: none;
  border-radius: 4px;
`

const Title = styled(Span)`
  font-size: 20px;
  font-weight: 900;
  color: #fff;
  margin: 10px 10px;
  align-self: self-start;
`;

const Content = styled.div`
  display: flex;
  flex-direction: column;

  width: 100%;
  height: 100%;
  border-radius: 5px;

  ${({ theme }) => theme.color.backgroundTertiary}
`;

const SubmitButton = styled(Button)`
  font-weight: 900;
  ${({ theme }) => theme.color.backgroundSoftBlue}
  ${({ theme }) => theme.color.neutral}
`;

// const onCancle = (e: React.MouseEvent<HTMLDivElement>) => {
//     if (backgroundRef.current === e.target) {
//       setIsProfileModalActive(false);
//     }
//   };

// const Modal = styled.div`
//   width: 100vw;
//   height: 100vh;

//   position: absolute;
//   top: 0;
//   left: 0;

//   background-color: rgba(0, 0, 0, 0.4);
// `;
