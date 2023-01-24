import React from "react";
import "../styles/form.css";
import {loginPlayer} from "../request/playerRequest";
import {errorNotification} from "../util/notification";
import {useNavigate} from "react-router-dom";
import FormPlayerData from "../components/FormPlayerData";

const Login = () => {
    const navigate = useNavigate();
    const handleSubmit = (values) => {
        loginPlayer(values.username, values.password)
            .then(() => {
                navigate("/create-game");
            })
            .catch(() => {
                errorNotification("Login failed", "Invalid credentials", "topRight");
            })
    };

    return (
        <FormPlayerData handleSubmit={handleSubmit} formName={"Login"} extraElements={
            <div className="form-group">
                <div className="col-sm-12" style={{textAlign: "center"}}>
                    <a href="/register">Don't have an account? Register here</a>
                </div>
            </div>
        } />
    );
}

export default Login;