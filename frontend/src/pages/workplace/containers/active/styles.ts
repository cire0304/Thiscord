import { styled } from "styled-components";

export const Container = styled.div`
    
    height: 100%;
    flex-grow: 1;
    
    ${({ theme }) => theme.flex.columnCenterCenter}
    ${({ theme }) => theme.color.backgroundTertiary}
`;