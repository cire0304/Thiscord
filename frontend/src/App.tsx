import React from "react";
import logo from "./logo.svg";
import "./App.css";
import {
  Route,
  RouterProvider,
  Routes,
  createBrowserRouter,
} from "react-router-dom";
import LoginPage from "./pages/login";
import { ThemeProvider } from "styled-components";
import theme from "./styles/theme";
import RegistgerPage from "./pages/register";
import WorkplacePage from "./pages/workplace";
import { Provider } from "react-redux";
import store from "./store";

function App() {
  return (
    <div className="App">
      <Provider store={store}>
        <ThemeProvider theme={theme}>
          <Routes>
            <Route path="/login" element={<LoginPage />} />
            <Route path="/" element={<LoginPage />} />
            <Route path="/register" element={<RegistgerPage />} />

            <Route path="/workspace" element={<WorkplacePage />}>

            </Route>
          </Routes>

        </ThemeProvider>
      </Provider>
    </div>
  );
}

export default App;
