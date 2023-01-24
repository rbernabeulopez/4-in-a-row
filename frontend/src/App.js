import {BrowserRouter, Route, Routes} from "react-router-dom";
import { Register } from "./view/Register";
import Login from "./view/Login";


const App = () => {
  return (
      <BrowserRouter>
          <Routes>
              <Route path="/register">
                  <Route index element={<Register/>}/>
              </Route>
              <Route path="/login">
                  <Route index element={<Login />}/>
              </Route>
          </Routes>
      </BrowserRouter>
  );
}

export default App;
