import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Container, Row, Col } from "react-bootstrap";
import { makeSocketConnection } from "../request/webSocketRequest";
import { createGame } from "../request/gameRequest";
import { Historical } from "../component/Historical";

export const CreateGame = () => {
  const [player1Id, setPlayer1Id] = useState(0);
  const navigate = useNavigate();

  const handleSubmit = async (event) => {
    event.preventDefault();
    let playerId = localStorage.getItem("playerId");
    let gameId = await createGame(playerId);
    navigate(`/table/${gameId}`)
  };

  const handleSubmit2 = (event) => {
    event.preventDefault();
    let playerId = localStorage.getItem("playerId");
    createGame(playerId);
    navigate("/table");

    // TODO: NAVIGATE TO GAME /: GAMEID
    // TODO: SUBSCRIBE TO GAME VIA SOCKET
  };

  const handleSubmit3 = (event) => {
    event.preventDefault();
    setPlayer1Id(localStorage.getItem("playerId"));
    createGame(player1Id);
    navigate("/historical");

    // TODO: NAVIGATE TO GAME /: GAMEID
    // TODO: SUBSCRIBE TO GAME VIA SOCKET
  };

  return (
    <Container>
      <Row>
        <Col sm={4}>
          <button class="btn btn-primary mt-5" onClick={handleSubmit}>
            Create Game
          </button>
        </Col>
      </Row>
      <Row>
        <Col sm={4}>
          <button class="btn btn-primary mt-5" onClick={handleSubmit2}>
            Join Game
          </button>
        </Col>
      </Row>
      <Row>
        <Col sm={4}>
          <button class="btn btn-primary mt-5" onClick={handleSubmit3}>
            My historical
          </button>
        </Col>
      </Row>
    </Container>
  );
};
