import React from "react";
import logo from "./logo.svg";
import "./App.css";
import { RouterProvider, createBrowserRouter } from "react-router-dom";
import LoginPage from "./pages/login";
import { ThemeProvider } from "styled-components";
import theme from "./styles/theme";

const router = createBrowserRouter([
  {
    path: "/login",
    element: <LoginPage />,
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
