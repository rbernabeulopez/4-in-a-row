import {BrowserRouter, Route, Routes} from "react-router-dom";

const App = () => {
  return (
      <BrowserRouter>
          <Routes>
              <Route path="/">
                  <Route index element={<>Hello world!</>}/>
              </Route>
          </Routes>
      </BrowserRouter>
  );
}

export default App;
