import {BrowserRouter, Route, Routes} from "react-router-dom";
import { Form } from "./component/Form";


const App = () => {
  return (
      <BrowserRouter>
          <Routes>
              <Route path="/form">
                  <Route index element={<Form/>}/>
              </Route>
          </Routes>
      </BrowserRouter>
  );
}

export default App;
