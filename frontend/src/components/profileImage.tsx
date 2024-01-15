

import React from "react";
import { styled } from "styled-components";

const Container = styled.img<{$size?:string}> `
  width: ${props => props.$size || "30px"}; 
  height: ${props => props.$size || "30px"}; 
  border-radius: 50%;
`;

const ProfileImage = ({ src, size }: { src: string, size?: string }) => {
  return <Container src={src} $size={size}></Container>;
};

export default ProfileImage;
