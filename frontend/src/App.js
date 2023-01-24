import { BrowserRouter, Route, Routes } from "react-router-dom";
import { Form } from "./view/Register";
import { Login } from "./view/Login
import { CreateGame } from "./create-game/CreateGame";

const App = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/register">
          <Route index element={<Register />} />
        </Route>
        <Route path="/create-game">
          <Route index element={<CreateGame />} />
        </Route>
        <Route path="/login">
        <Route index element={<Login />}/>
        </Route>
      </Routes>
    </BrowserRouter>
  );
};

export default App;
