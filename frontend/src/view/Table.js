import React, { useEffect, useState} from "react";
import '../styles/table.css'
import { Button, Col, List, Row, Spin } from "antd";
import GameRow from "../component/GameRow";
import { useNavigate, useParams } from "react-router-dom";
import { makeSocketConnection, setMovementEventHandler, sendEvent } from "../request/webSocketRequest";
import { errorNotification } from "../util/notification";
import { getGameById } from "../request/gameRequest";
import Modal from 'react-bootstrap/Modal';

export const Table = () => {

  const { gameId } = useParams();

  const navigate = useNavigate();

  useEffect(() => {
    let playerId = localStorage.getItem("playerId")
    makeSocketConnection(gameId, playerId);
  }, [])

  const [isPlayerTurn, setIsPlayerTurn] = useState(false);

  const [data, setData] = useState({})

  const [board, setBoard] = useState([]);

  const [show, setShow] = useState(true);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const [showWin, setShowWin] = useState(false);

  const [showLose, setLose] = useState(false);

  // Starts new game
  const initBoard = (movements = [], players) => {
    // Create a blank 6x7 matrix
    let generatedboard = [];
    for (let r = 0; r < 6; r++) {
      let row = [];
      for (let c = 0; c < 7; c++) {
        const movement = movements.find((movement) => movement.row === r && movement.col === c);
        if (movement == null) {
          row.push(0);
        } else if (movement.player.id === players[0].id) {
          row.push(1);
        } else if (movement.player.id === players[1].id) {
          row.push(2);
        } else {
          row.push(0);
        }
      }
      generatedboard.push(row);
    }
    setBoard(generatedboard);

  }



  useEffect(() => {
    const startGame = async ()=> {
      let playerId = localStorage.getItem("playerId")
      makeSocketConnection(gameId, playerId);
      const game = await getGameById(gameId);

    if (!game.finished) {
      if (game.movements.length == 0 && game.players[0].id == localStorage.getItem("playerId")) {
        setIsPlayerTurn(true);
      } else if (game.movements.length > 0 && game.movements.at(-1).player.id != localStorage.getItem("playerId")) {
        setIsPlayerTurn(true);
      }
    }  else if(game.winner.id != localStorage.getItem("playerId") ){
      setLose(true);
    } else {
      console.log(game)
      setShowWin(true);
    }



      setData({
        players: game.players,
        movements: game.movements,
        winner: game.winner,
        finished: game.finished
      })
      setMovementEventHandler(receiveMovement);
      initBoard(game.movements, game.players);
      if(game.players.length == 2){
        handleClose();
      }
    }
    startGame()
  }, [])



  const putPiece = (columnIndex) => {
    console.log(isPlayerTurn + " turno del jugador")
    if (!isPlayerTurn) {
      return
    }
    let r = 5;
    for (r; r >= 0; r--) {
      if (board[r][columnIndex] == 0) {
        break;
      }
    }
    if (r < 0) {
      errorNotification("Error puting piece", "Column is full", 'topRight');
      return;
    }

    const updateData = { ...data, movements: [...data.movements, { row: r, col: columnIndex, player: { id: parseInt(localStorage.getItem("playerId")) } }] };
    setData(updateData);
    initBoard(updateData.movements, updateData.players);

    const movement = {
      row: r,
      col: columnIndex,
      playerId: localStorage.getItem("playerId"),
      gameId: gameId
    };
    setIsPlayerTurn(false);
    sendEvent('/make-movement', movement);
  }


  const receiveMovement = (movementReceived) => {
    if (movementReceived.joinedPlayer != undefined) {
      console.log(movementReceived + "Entra un nuevo jugador")
      handleClose();
      setData(prevState => {
        const updateData = { ...prevState, players: [...prevState.players, {id: movementReceived.joinedPlayer.playerId, name: movementReceived.joinedPlayer.name}] };
        //initBoard(updateData.movements, updateData.movementReceived);
        setIsPlayerTurn(true);
        return updateData;
      });
      return;
    }
    console.log(movementReceived.gameFinished + " esto es si ha acabado la partida");
    if (movementReceived.gameFinished) {
      setIsPlayerTurn(false);
      if(movementReceived.winnerId != localStorage.getItem("playerId") ){
        setLose(true);
      } else {
        setShowWin(true);
      }
    }
    if (movementReceived.playerId == localStorage.getItem("playerId")) {
      return;
    }
    console.log(movementReceived)

    setData(prevState => {
      console.log(prevState)
      const updateData = { ...prevState, movements: [...prevState.movements, { row: movementReceived.row, col: movementReceived.col, player: { id: movementReceived.playerId } }] };
      initBoard(updateData.movements, updateData.players);
      setIsPlayerTurn(true);
      return updateData;
    });
  }

  if (data === {}) {
    return <div>loading</div>
  }

  return (
    <div style={{ padding: '50px' }}>

      <Modal show={show}>
        <Modal.Header style={{ justifyContent: "center" }} >
          <h3 style={{ color: "blue" }}> Waiting other player</h3>
        </Modal.Header>
        <Modal.Body>
          <Spin size="large"> <div className="content" />
          </Spin>
          <br />
        </Modal.Body>
      </Modal>

      <Modal show={showWin}>
        <Modal.Header style={{ justifyContent: "center" }} >
          <h1> !You win!</h1>
        </Modal.Header>
        <Modal.Body>
          <h2>Crack,genio,titán,figura,vivaldi de los vivaldi.</h2>
          <Button onClick={() => navigate("/")}>
            Back to home
          </Button>
        </Modal.Body>

      </Modal>

      <Modal show={showLose}>
        <Modal.Header style={{ justifyContent: "center" }} >
          <h1> !You lose!</h1>
        </Modal.Header>
        <Modal.Body>
          <h3>You are a loser.</h3>
          <Button onClick={() => navigate("/")}>
            Back to home
          </Button>
        </Modal.Body>
      </Modal>

      <Row>
        <h1>Connect 4</h1>
      </Row>
      <Row>
        <Col span={6}>
          <List header={<b>Players</b>}
            bordered style={{ width: 300, marginTop: 20 }} dataSource={data.players}
            renderItem={(item) => {

              return (
                <List.Item>{item.name}</List.Item>
              )
            }} />
        </Col>
        <Col span={18}> <table>
          <thead>
          </thead>
          <tbody>
            {board.map((row, i) => (
              <GameRow key={i} row={row} putPiece={putPiece} isPlayerTurn={isPlayerTurn} />))}
          </tbody>
        </table>
        </Col>
      </Row>
      <Row>
        {isPlayerTurn ? <h1 style={{ color: "darkblue" }}>Es tu turno</h1> : <h1 style={{ color: "darkblue" }}>Te toca esperar...</h1>}
      </Row>
    </div>
  )
}
