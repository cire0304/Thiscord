import { FriendDTO } from "../../../store/slices/friendSlice";
import { styled } from "styled-components";
import ProfileImage from "../../../components/profileImage";
import Span from "../../../components/span";
import Checkbox from "../../../components/checkBox/checkbox";

export default function FriendBar({ friend }: { friend: FriendDTO }) {
  return (
    <GroupCheckbox value={friend.userId.toString()} checked={false}>
      <Container>
        <ProfileImage
          src={`https://gravatar.com/avatar/${friend.userId}?d=identicon`}
          size="35px"
        />
        <Nickname>{friend.nickname}</Nickname>
      </Container>
    </GroupCheckbox>
  );
}

const Container = styled.div`
  display: flex;
  align-items: center;
  padding: 5px 10px;

  &:hover {
    background-color: #36393f;
  }
`;

const Nickname = styled(Span)`
  margin-left: 10px;
  color: #bdbdbd;
`;

const GroupCheckbox = styled(Checkbox)`
  position: absolute;
  top: 0;
  left: 0;
`;
