import React from "react";
import * as S from "./styles";
import { ReactComponent as ChannelIcon } from "../../../../assets/icons/channel.svg";

interface Active {
  name: string;
  active: boolean;
}

const Nav = () => {

  const initialButtonProps = [{ name: "온라인", active: true}, { name: "모두", active: false}, { name: "대기중", active: false}, { name: "차단 목록", active: false}];
  const [buttonProps, setButtonProps] = React.useState<Active[]>(initialButtonProps);

  return (
    <S.Container>
      <ChannelIcon />
      <S.Text>친구</S.Text>

      <S.Navigates>

        {
          buttonProps.map((button, index) => {
            return (
              <S.Button
                key={index}
                active={button.active}
                onClick={() => {
                  const newActives = buttonProps.map((act, i) => {
                    if (index === i) {
                      act.active = true;
                    } else {
                      act.active = false;
                    }
                    return act;
                  });
                  setButtonProps(newActives);
                }}
              >
                {button.name}
              </S.Button>
            );
          })
        }
        <S.FriendButton>친구 추가하기</S.FriendButton>
      </S.Navigates>
    </S.Container>
  );
};

export default Nav;
