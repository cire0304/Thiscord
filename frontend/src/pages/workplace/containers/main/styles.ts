import { styled } from "styled-components";

export const Container = styled.div`
    width: 800px;
    height: 100%;

    border-right: 1px solid #404147;
    
    ${({ theme }) => theme.flex.columnCenterCenter}
    ${({ theme }) => theme.color.backgroundTertiary}
`;