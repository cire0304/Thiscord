import { ReactComponent as ChannelIcon } from "../../../assets/icons/channel.svg";
import { useDispatch, useSelector } from "react-redux";
import { styled } from "styled-components";
import { activeById } from "../../../store";
import { ViewState } from "../../../store/slices/viewState";
import ProfileImage from "../../../components/profileImage";

const Nav = () => {
  const dispatch = useDispatch();
  const viewState = useSelector((state: any) => state.viewState) as ViewState;

  // 상대방의 정보 가져오기
  const userId = 123;
  const userNickname = "test";

  return (
    <Container>
      <ProfileImage src={`https://gravatar.com/avatar/${userId}?d=identicon`} />
      <Content></Content>
    </Container>
  );
};

export default Nav;

const Container = styled.div`
  height: 50px;
  padding: 10px;

  display: flex;
  align-items: center;
  justify-content: flex-start;

  flex-grow: 1;
  border-bottom: 1px solid ${({ theme }) => theme.color.border};
  ${({ theme }) => theme.color.backgroundTertiary}
`;

const Content = styled.div`
    display: flex;

`