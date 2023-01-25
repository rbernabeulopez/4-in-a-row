import {BrowserRouter, Route, Routes, useNavigate} from "react-router-dom";
import { Register } from "./view/Register";
import Login from "./view/Login";
import NotFound from "./view/NotFound";
import {useEffect} from "react";
import { Historical } from "./component/Historical"
import { CreateGame } from "./create-game/CreateGame";
import {Table} from "./components/Table/Table";


const Redirect = () => {
    const navigate = useNavigate();
    useEffect(() =>
        navigate(localStorage.getItem('playerToken') ? '/create-game' : '/login'),
    []);
}

const App = () => {
  return (
import {BrowserRouter, Route, Routes} from "react-router-dom";
import { Example } from "./components/Example/Example";
import { Table } from "./components/Table/Table";

import { Register } from "./view/Register";
import Login from "./view/Login";

import { BrowserRouter, Route, Routes } from "react-router-dom";
import { Form } from "./view/Register";
import { Login } from "./view/Login
import { Historical} from "./component/Historical"
import { CreateGame } from "./create-game/CreateGame";

const App = () => {
  return (
      <BrowserRouter>
          <Routes>
              <Route path="/">
                  <Route index element={<Example/>}/>
              <Route path="/register">
                  <Route index element={<Register/>}/>
              </Route>
              <Route path="/login">
                  <Route index element={<Login />}/>
              </Route>
              <Route path="/table">
                <Route index element={<Table/>}/>
              </Route>
          </Routes>
      </BrowserRouter>
    <BrowserRouter>
        <Routes>
            <Route path="/" element={<Redirect />} />
            <Route path="/register">
                <Route index element={<Register />} />
            </Route>
            <Route path="/create-game">
                <Route index element={<CreateGame />} />
            </Route>
            <Route path="/login">
                <Route index element={<Login />}/>
            </Route>
            <Route path="/historical">
                <Route index element={<Historical />}/>
            </Route>
            <Route path="/table">
                <Route index element={<Table/>}/>
            </Route>
            <Route path="*" element={<NotFound/>}/>
        </Routes>
    </BrowserRouter>
  );
}

export default App;
