import {BrowserRouter, Route, Routes, useNavigate} from "react-router-dom";
import { Register } from "./view/Register";
import Login from "./view/Login";
import NotFound from "./view/NotFound";
import {useEffect} from "react";


const Redirect = () => {
    const navigate = useNavigate();
    useEffect(() =>
        navigate(localStorage.getItem('playerToken') ? '/create-game' : '/login'),
    []);
}

const App = () => {
  return (
      <BrowserRouter>
          <Routes>
              <Route path="/" element={<Redirect />} />
              <Route path="/register">
                  <Route index element={<Register/>}/>
              </Route>
              <Route path="/login">
                  <Route index element={<Login />}/>
              </Route>
              <Route path="*" element={<NotFound/>}/>
          </Routes>
      </BrowserRouter>
  );
}

export default App;
