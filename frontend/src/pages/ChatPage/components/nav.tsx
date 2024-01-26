import { styled } from "styled-components";
import ProfileImage from "../../../components/profileImage";
import Span from "../../../components/span";
import { DmRoom } from "../../../services/RoomService";


const Nav = ({ room }: { room: DmRoom }) => {
  return (
    <Container>
      <ProfileImage
        src={`https://gravatar.com/avatar/${room.otherUser.userId}?d=identicon`}
        size="25px"
      />
      <Nickname>{room.otherUser.nickname}</Nickname>
    </Container>
  );
};

export default Nav;

const Container = styled.div`
  width: 100%;
  height: 50px;
  min-height: 50px;
  padding: 10px;

  display: flex;
  align-items: center;
  justify-content: flex-start;

  border-bottom: 1px solid;
  ${({ theme }) => theme.color.backgroundTertiary}
`;

const Nickname = styled(Span)`
  padding-left: 10px;
  color: white;
`;
