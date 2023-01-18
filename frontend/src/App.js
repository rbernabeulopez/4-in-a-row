import {BrowserRouter, Route, Routes} from "react-router-dom";
import { Table } from './components/Table/Table';

const App = () => {
  return (
      <BrowserRouter>
          <Routes>
              <Route path="/">
                  <Route index element={<Table/>}/>
              </Route>
          </Routes>
      </BrowserRouter>
  );
}

export default App;
