import { ReactComponent as ChannelIcon } from "../../../assets/icons/channel.svg";
import { useDispatch, useSelector } from "react-redux";
import { styled } from "styled-components";
import { activeById } from "../../../store";
import { ViewState } from "../../../store/slices/viewState";
import ProfileImage from "../../../components/profileImage";
import Span from "../../../components/span";
import { DmRoom } from "../../../api/roomAPI";

const Nav = ({ room }: { room: DmRoom }) => {
  // TODO: status user info code would be here

  return (
    <Container>
      <ProfileImage
        src={`https://gravatar.com/avatar/${room.otherUserId}?d=identicon`}
        size="25px"
      />
      <Nickname>{room.otherUserNickname}</Nickname>
    </Container>
  );
};

export default Nav;

const Container = styled.div`
  width: 100%;
  height: 50px;
  padding: 10px;

  display: flex;
  align-items: center;
  justify-content: flex-start;

  border-bottom: 1px solid ${({ theme }) => theme.color.border};
  ${({ theme }) => theme.color.backgroundTertiary}
`;

const Nickname = styled(Span)`
  padding-left: 10px;
  color: white;
`;
