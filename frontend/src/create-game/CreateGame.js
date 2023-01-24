import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Container, Row, Col } from "react-bootstrap";

import { createGame } from "../request/gameRequest";

export const CreateGame = () => {
  const [player1Id, setPlayer1Id] = useState(0);
  const navigate = useNavigate();

  const handleSubmit = (event) => {
    event.preventDefault();
    setPlayer1Id(localStorage.getItem("playerId"));
    createGame(player1Id);

    // TODO: NAVIGATE TO GAME /: GAMEID
    // TODO: SUBSCRIBE TO GAME VIA SOCKET
  };

  return (
    <Container>
      <Row>
        <Col sm={4}>
          <button class="btn btn-primary" onClick={handleSubmit}>
            Create Game
          </button>
        </Col>
      </Row>
    </Container>
  );
};
