import { BrowserRouter, Route, Routes } from "react-router-dom";
import { Form } from "./component/Form";
import { CreateGame } from "./create-game/CreateGame";

const App = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/register">
          <Route index element={<Form />} />
        </Route>
        <Route path="/create-game">
          <Route index element={<CreateGame />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
};

export default App;
