import {BrowserRouter, Route, Routes, useNavigate} from "react-router-dom";
import { Register } from "./view/Register";
import Login from "./view/Login";
import NotFound from "./view/NotFound";
import {useEffect} from "react";
import { Historical } from "./component/Historical"
import { CreateGame } from "./view/CreateGame";
import {Table} from "./view/Table";
import JoinGame from "./view/JoinGame";
import PageLayout from "./component/layout/PageLayout";


const Redirect = () => {
    const navigate = useNavigate();
    useEffect(() =>
        navigate(localStorage.getItem('playerToken') ? '/create-game' : '/login'),
    []);
}


const App = () => {

   
  return (
    <BrowserRouter>
        <PageLayout>
            <Routes>
                <Route path="/" element={<Redirect/>} />
                <Route path="/register">
                    <Route index element={<Register />} />
                </Route>
                <Route path="/login">
                    <Route index element={<Login />} />
                </Route>
                <Route path="/create-game">
                    <Route index element={<CreateGame />} />
                </Route>
                <Route path="/join-game">
                    <Route index element={<JoinGame />} />
                </Route>
                <Route path="/historical">
                    <Route index element={<Historical />}/>
                </Route>
                <Route path="/summary/:gameId">
                    <Route index element={<Summary />}/>
                </Route>
                <Route path="/table/:gameId">
                    <Route index element={<Table/>}/>
                </Route>
                <Route path="*" element={<NotFound/>}/>
            </Routes>
        </PageLayout>
    </BrowserRouter>
  );
}

export default App;
