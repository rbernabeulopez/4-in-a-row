import {BrowserRouter, Route, Routes} from "react-router-dom";
import { Form } from "./component/Form";
import Login from "./view/Login";


const App = () => {
  return (
      <BrowserRouter>
          <Routes>
              <Route path="/register">
                  <Route index element={<Form/>}/>
                  <Route path="/login" element={<Login />}/>
              </Route>
          </Routes>
      </BrowserRouter>
  );
}

export default App;
