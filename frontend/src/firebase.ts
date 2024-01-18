// Import the functions you need from the SDKs you need
import { getDatabase } from "@firebase/database";
import { initializeApp } from "firebase/app";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
const firebaseConfig = {
  apiKey: "AIzaSyClVGhUnznOCJsY-eZOJ0BczESwbajxc9c",
  authDomain: "react-chat-app-2633a.firebaseapp.com",
  databaseURL: "https://react-chat-app-2633a-default-rtdb.asia-southeast1.firebasedatabase.app",
  projectId: "react-chat-app-2633a",
  storageBucket: "react-chat-app-2633a.appspot.com",
  messagingSenderId: "728231955593",
  appId: "1:728231955593:web:7cbf93d540f109957d4a58"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);

export default app;

export const db = getDatabase(app);
