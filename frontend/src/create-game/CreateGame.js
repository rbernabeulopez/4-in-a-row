import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Container, Row, Col } from "react-bootstrap";

import { createGame } from "../request/gameRequest";

export const CreateGame = () => {
  const [player1Id, setPlayer1Id] = useState(0);
  const navigate = useNavigate();

  const handleSubmit = (event) => {
    event.preventDefault();
    createGame(player1Id);
    //       .then(
    //   navigate("/game/" + localStorage.getItem("gameId"))
    // );
  };

  return (
    <Container>
      <Row>
        <Col sm={4}>
          <form class="mt-5" onSubmit={handleSubmit}>
            <div class="mb-3 mt-5">
              <label for="name" class="form-label">
                Jugador 1
              </label>
              <input
                type="text"
                class="form-control"
                value={player1Id}
                onChange={(e) => setPlayer1Id(e.target.value)}
                aria-describedby="player1Id"
              />
            </div>
            <button type="submit" class="btn btn-primary">
              Create Game
            </button>
          </form>
        </Col>
      </Row>
    </Container>
  );
};
