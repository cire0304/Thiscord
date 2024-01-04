import { styled } from "styled-components";

export const Container = styled.div`
    min-width: 250px;

    height: 100%;
    
    ${({ theme }) => theme.flex.columnStartCenter}
    ${({ theme }) => theme.color.backgroundPrimary}
`;

export const Title = styled.span`
    padding: 10px 15px;
    ${({ theme }) => theme.color.secondary}
    align-self: start;

`;

export const RoomsWrapper = styled.div`
    width: 100%;
    flex-grow: 1;
    ${({ theme }) => theme.flex.columnStartCenter}

`;
