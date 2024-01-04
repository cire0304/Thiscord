import React from "react";
import { styled } from "styled-components";

const Container = styled.img`
  width: 30px;
  margin-right: 10px;
  border-radius: 50%;
`;

const ProfileImage = ({ src }: { src: string }) => {
  return <Container src={src}></Container>;
};

export default ProfileImage;
