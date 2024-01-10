import React from "react";
import logo from "./logo.svg";
import "./App.css";
import { RouterProvider, createBrowserRouter } from "react-router-dom";
import LoginPage from "./pages/login";
import { ThemeProvider } from "styled-components";
import theme from "./styles/theme";
import RegistgerPage from "./pages/register";
import WorkplacePage from "./pages/workplace";
import { Provider } from "react-redux";
import store from "./store";
import UserGuard from "./utils/userGuard";
import FetchRoomList from "./utils/fetchRoomList";

const router = createBrowserRouter([
  {
    path: "/login",
    element: <LoginPage />,
  },
  {
    path: "/register",
    element: <RegistgerPage />,
  },
  {
    path: "/workspace",
    element: (
      <UserGuard>
        <WorkplacePage />
      </UserGuard>
    ),
  },
  {
    path: "/",
    element: (
      <FetchRoomList>
        {" "}
        <UserGuard>
          <WorkplacePage />
        </UserGuard>
      </FetchRoomList>
    ),
  },
]);

function App() {
  return (
    <div className="App">
      <Provider store={store}>
        <ThemeProvider theme={theme}>
          <RouterProvider router={router}></RouterProvider>
        </ThemeProvider>
      </Provider>
    </div>
  );
}

export default App;
