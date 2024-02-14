importScripts("https://www.gstatic.com/firebasejs/8.0.0/firebase-app.js");
importScripts("https://www.gstatic.com/firebasejs/8.0.0/firebase-messaging.js");

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
firebase.initializeApp(firebaseConfig);

const messaging = firebase.messaging();

//백그라운드 서비스워커 설정
messaging.onBackgroundMessage((payload) => {
    console.log(
      "[firebase-messaging-sw.js] Received background message ",
      payload
    );
    
    // Customize notification here
    const notificationTitle = "Background Message Title";
    const notificationOptions = {
      body: payload,
      icon: "/firebase-logo.png",
    };
    
    self.registration.showNotification(notificationTitle, notificationOptions);
});
