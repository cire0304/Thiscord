import React from "react";
import logo from "./logo.svg";
import "./App.css";
import { RouterProvider, createBrowserRouter } from "react-router-dom";
import LoginPage from "./pages/login";
import { ThemeProvider } from "styled-components";
import theme from "./styles/theme";
import RegistgerPage from "./pages/register";
import WorkplacePage from "./pages/workplace";

const router = createBrowserRouter([
  {
    path: "/login",
    element: <LoginPage />,
  },
  {
    path: "/register",
    element: <RegistgerPage/>,
  },
  {
    path: "/workspace",
    element: <WorkplacePage/>,
  },
]);

function App() {
  return (
    <div className="App">
      <ThemeProvider theme={theme}>
        <RouterProvider router={router}></RouterProvider>
      </ThemeProvider>
    </div>
  );
}

export default App;
