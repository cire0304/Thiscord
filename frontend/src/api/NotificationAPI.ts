import { getMessaging, getToken } from "@firebase/messaging";
import axiosInstance from "./axios";
import { VAPID_KEY } from "../constants/constants";

const putProfile = async () => {
    // TODO: extract to .env
    const vapidKey = VAPID_KEY;
    const messaging = getMessaging();
  
    const fcmToken =  await getToken(messaging, {vapidKey: vapidKey})
    try {
      if (fcmToken) {
        // TODO: extract this function
        await axiosInstance.put("/notifications/profiles/me", {fcmToken: fcmToken});
      } else {
        alert(`No registration token available. Request permission to generate one.`);  
      }
    } catch (error) {
      alert(`로그인 중 에러 발생! : ${error}`);
    }
  };

const  NotificationAPI = {
    putProfile,
}

export default NotificationAPI;
