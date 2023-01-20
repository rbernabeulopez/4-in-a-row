import React from 'react';
import './form.css'
import {Container, Row, Col} from 'react-bootstrap'
import { useState } from 'react';
import { createPlayer } from '../request/playerRequest';
import { useNavigate } from 'react-router-dom';

export const Form = () => {

  const [name, setName] = useState("");
  const [password,setPassword] = useState("");

  const navigate = useNavigate();


  const handleSubmit = (event) => {
    event.preventDefault();
    createPlayer(name, password)
    .then(navigate("/createGame"))
  }


    return(
      <Container>
        <Row>
          <Col sm={4}>
          <form class="mt-5" onSubmit={handleSubmit}>
            <div class="mb-3 mt-5">
              <label for="name" class="form-label">Name Player</label>
              <input type="text" class="form-control" value={name} onChange={(e)=> setName(e.target.value)} aria-describedby="name"/>
              <div id="name" class="form-text">Your name could scare your enemies</div>
            </div>
            <div class="mb-3">
              <label for="password" class="form-label">Password</label>
              <input type="password" class="form-control" value={password} onChange={(e)=> setPassword(e.target.value)}/>
            </div>
            <div class="mb-3 form-check">
              <input type="checkbox" class="form-check-input" id="exampleCheck1"/>
              <label class="form-check-label" for="exampleCheck1">Check me out</label>
            </div>
            <button type="submit" class="btn btn-primary">Register</button>
          </form>
          </Col>
        </Row>
      </Container>
    );
}