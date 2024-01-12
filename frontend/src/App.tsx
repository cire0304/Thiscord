import "./App.css";
import { Route, Routes } from "react-router-dom";
import LoginPage from "./pages/login";
import { ThemeProvider } from "styled-components";
import theme from "./styles/theme";
import RegistgerPage from "./pages/register";

import { Provider } from "react-redux";
import store from "./store";
import Layout from "./components/layout/layout";
import FriendPage from "./pages/friend/friendPage";
import ChatPage from "./pages/chat/chatPage";

function App() {
  return (
    <div className="App">
      <Provider store={store}>
        <ThemeProvider theme={theme}>
          <Routes>
            <Route path="/login" element={<LoginPage />} />
            <Route path="/" element={<LoginPage />} />
            <Route path="/register" element={<RegistgerPage />} />

            <Route path="/workspace" element={<Layout />} >
              <Route path="me" element={<FriendPage />} />
              <Route path="rooms/:roomId" element={<ChatPage />} />
            </Route>
          </Routes>
        </ThemeProvider>
      </Provider>
    </div>
  );
}

export default App;
