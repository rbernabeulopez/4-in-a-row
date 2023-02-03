import '../styles/form.css'
import React from 'react';
import { createPlayer } from '../request/playerRequest';
import { useNavigate } from 'react-router-dom';
import FormPlayerData from "../component/FormPlayerData";
import {errorNotification} from "../util/notification";

export const Register = () => {
  const navigate = useNavigate();

  const handleSubmit = (values) => {
    createPlayer(values.username, values.password)
        .then(() => navigate('/login'))
        .catch((res) => {
          errorNotification("Register failed", res.response.data.message, "topRight");
        })  }


  return (
      <FormPlayerData handleSubmit={handleSubmit} formName={"Register"} extraElements={
        <div className="form-group">
          <div className="col-sm-12" style={{textAlign: "center"}}>
            <a href="/login">Already have an account? Login</a>
          </div>
        </div>
      } />
  );
}