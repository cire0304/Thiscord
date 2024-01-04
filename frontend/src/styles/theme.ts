import { css } from "styled-components";

const fontFormat = {
  largetitle: css`
    font-size: 34px;
    line-height: 41px;
    font-weight: 400;
  `,
  title1: css`
    font-size: 28px;
    line-height: 34px;
    font-weight: 400;
  `,
  title2: css`
    font-size: 22px;
    line-height: 28px;
    font-weight: 400;
  `,
  title3: css`
    font-size: 20px;
    line-height: 25px;
    font-weight: 400;
  `,
  headline: css`
    font-size: 17px;
    line-height: 22px;
    font-weight: 600;
  `,
  body: css`
    font-size: 17px;
    line-height: 24px;
    font-weight: 400;
  `,
  content: css`
    font-size: 17px;
    line-height: 22px;
    font-weight: 400;
  `,
  callout: css`
    font-size: 16px;
    line-height: 21px;
    font-weight: 400;
  `,
  subhead: css`
    font-size: 15px;
    line-height: 20px;
    font-weight: 400;
  `,
  footnote: css`
    font-size: 13px;
    line-height: 18px;
    font-weight: 400;
  `,
  caption1: css`
    font-size: 12px;
    line-height: 16px;
    font-weight: 400;
  `,
  caption2: css`
    font-size: 11px;
    line-height: 13px;
    font-weight: 400;
  `,
};

const color = {
  primary: css`
    color: #b9bbbe;
  `,
  secondary: css`
    color: #72767d;
  `,
  neutral: css`
    color: #ffffff;
  `,
  neutralBlue: css`
    color: #1e90ff;
  `,
  systemDefault: css`
    color: #007aff;
  `,
  systemWarning: css`
    color: #ff3b30;
  `,
  backgroundPrimary: css`
    background-color: #2a2d31;
  `,
  backgroundSecondary: css`
    background-color: #18191c;
  `,
  backgroundTertiary: css`
    background-color: #303338;
  `,
  backgroundSoftBlue: css`
    background-color: #5865f2;
  `,
};

const flex = {
  rowCenterCenter: css`
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
  `,
  rowStartCenter: css`
    display: flex;
    flex-direction: row;
    justify-content: flex-start;
    align-items: center;
  `,
  columnCenterCenter: css`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  `,
  columnCenterStart: css`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: flex-start;
  `,
  columnStartCenter: css`
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: center;
  `,
};

const position = {
  center: css`
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
  `,
};

const theme = {
  color,
  flex,
  position,
  fontFormat,
};

export default theme;
