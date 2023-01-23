import {BrowserRouter, Route, Routes} from "react-router-dom";
import { Example } from "./components/Example/Example";
import { Table } from "./components/Table/Table";


const App = () => {
  return (
      <BrowserRouter>
          <Routes>
              <Route path="/">
                  <Route index element={<Example/>}/>
              </Route>
              <Route path="/table">
                <Route index element={<Table/>}/>
              </Route> 
          </Routes>
      </BrowserRouter>
  );
}

export default App;
