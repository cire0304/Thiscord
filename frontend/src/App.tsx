import "./App.css";
import { Route, Routes } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import { ThemeProvider } from "styled-components";
import theme from "./styles/theme";
import RegistgerPage from "./pages/RegisterPage";

import { Provider } from "react-redux";
import store, { persistor } from "./store";
import Layout from "./components/layout/layout";
import FriendPage from "./pages/FriendPage/friendPage";
import ChatPage from "./pages/ChatPage/chatPage";
import { PersistGate } from "redux-persist/integration/react";

function App() {
  return (
    <div className="App">
      <Provider store={store}>
          <PersistGate loading={null} persistor={persistor}>
        <ThemeProvider theme={theme}>
          <Routes>
            <Route path="/login" element={<LoginPage />} />
            <Route path="/" element={<LoginPage />} />
            <Route path="/register" element={<RegistgerPage />} />

            <Route path="/workspace" element={<Layout />}>
              <Route path="me" element={<FriendPage />} />
              <Route path="rooms/:roomId" element={<ChatPage />} />
            </Route>
          </Routes>
        </ThemeProvider>
        </PersistGate>
      </Provider>
    </div>
  );
}

export default App;
