import {BrowserRouter, Route, Routes} from "react-router-dom";
import { Example } from "./components/Example/Example";
import { Table } from "./components/Table/Table";

import { Register } from "./view/Register";
import Login from "./view/Login";


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
  );
}

export default App;
