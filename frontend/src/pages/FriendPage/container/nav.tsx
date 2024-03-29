import { ReactComponent as ChannelIcon } from "../../../assets/icons/channel.svg";
import { styled } from "styled-components";
import { activeById } from "../../../store";
import { useAppDispatch, useAppSelector } from "../../../hooks/redux";
import Span from "../../../components/span";
import GroupButton from "../components/groupAddButton";



const Nav = () => {
  const dispatch = useAppDispatch();
  const viewState = useAppSelector((state) => state.viewState);

  return (
    <Container>
      <ChannelIcon width="30" height="30" />
      <Text>친구</Text>

      <Navigates>
        {viewState.infos.map((item, index) => {
          return (
            <Button
              key={index}
              active={viewState.infos[index].active}
              onClick={() => {
                dispatch(activeById({ id: index }));
              }}
            >
              {item.name}
            </Button>
          );
        })}
      </Navigates>
      <GroupButton></GroupButton>
    </Container>
  );
};

export default Nav;

export const Container = styled.div`
  height: 50px;
  padding: 10px;

  display: flex;
  align-items: center;
  justify-content: flex-start;
  
  position: relative;

  flex-grow: 1;
  border-bottom: 1px solid;
  ${({ theme }) => theme.color.backgroundTertiary};

`;

export const Text = styled(Span)`
  color: white;
  margin: 0px 5px;
  padding: 0px 10px;
  border-right: 1px solid white;
`;

export const Navigates = styled.div`
  ${({ theme }) => theme.flex.rowStartCenter}
`;

export const Button = styled.button<{ active?: boolean }>`
  ${({ theme }) => theme.color.neutral}
  ${({ theme }) => theme.flex.rowStartCenter}
  ${({ theme }) => theme.color.backgroundTertiary}

  padding: 3px 10px;
  border: none;
  border-radius: 5px;

  cursor: pointer;
  transition: 0.2s;

  &:hover {
    background-color: #3e4249;
  }

  &:active {
    background-color: #2e3136;
  }

  ${({ active }) =>
    active &&
    `
        background-color: #3e4249;
    `}
`;

export const FriendButton = styled.button`
  ${({ theme }) => theme.color.neutral}
  ${({ theme }) => theme.flex.rowStartCenter}
  background-color: #248046;
  margin-left: 5px;
  padding: 3px 10px;
  border: none;
  border-radius: 5px;

  cursor: pointer;
  transition: 0.2s;

  &:hover {
    background-color: #349056;
  }

  &:active {
    background-color: #2e3136;
  }
`;
