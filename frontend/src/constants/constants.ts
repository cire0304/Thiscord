// =============== Login ==================
function getServerUrl() {
  if (process.env.REACT_APP_ENV === "DEMO") {
    return process.env.REACT_APP_SERVER_URL_DEMO;
  }
  return process.env.REACT_APP_SERVER_URL;

}

export const SERVER_URL = getServerUrl()
export const GOOGLE_LOGIN_URL = `${SERVER_URL}/oauth2/authorization/google`;


// =============== Notification ==================
export const VAPID_KEY = process.env.REACT_APP_VAPID_KEY;

// =============== Chat ==================
function getChatServerEndPoint() {
  if (process.env.REACT_APP_ENV === "DEMO") {
    return process.env.REACT_APP_CHAT_SERVER_END_POINT_DEMO;
  }
  return process.env.REACT_APP_CHAT_SERVER_END_POINT;
}

function getChatServerUrl() {
  if (process.env.REACT_APP_ENV === "DEMO") {
    return process.env.REACT_APP_CHAT_SERVER_URL_DEMO;
  }
  return process.env.REACT_APP_CHAT_SERVER_URL;

}

export const CHAT_SERVER_END_POINT = getChatServerEndPoint();
export const CHAT_SERVER_URL = getChatServerUrl()
