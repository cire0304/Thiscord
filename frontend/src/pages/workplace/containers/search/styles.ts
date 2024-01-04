import { styled } from "styled-components";

export const Container = styled.div`
    width: 250px;
    height: 100%;
    padding: 10px;
    
    ${({ theme }) => theme.flex.columnCenterCenter}
    ${({ theme }) => theme.color.backgroundPrimary}
`;