import React, { useEffect, useState} from "react";
import { useParams } from "react-router-dom";
import {makeSocketConnection, setMovementEventHandler} from "../../request/webSocketRequest";
import './table.css'
import {Col, List, Row} from "antd";
import GameRow from "../../component/GameRow";
import { sendEvent } from "../../request/webSocketRequest";
import { errorNotification } from "../../util/notification";
import { getGameById } from "../../request/gameRequest";


export const Table =() => {


  const { gameId } = useParams();

  useEffect(() => {
    let playerId = localStorage.getItem("playerId")
    makeSocketConnection(gameId, playerId);
  }, [])

    const [isPlayerTurn, setIsPlayerTurn] = useState(false);

    const [data, setData] = useState({})

    const [board, setBoard] = useState([]);

    // Starts new game
    const initBoard= (movements =  [], players) => {
        // Create a blank 6x7 matrix
        let generatedboard = [];
        for (let r = 0; r < 6; r++) {
          let row = [];
          for (let c = 0; c < 7; c++) {
            const movement = movements.find((movement) => movement.row === r && movement.col === c);
            if(movement == null){
                row.push(0);
            } else if(movement.player.id === players[0].id){
                row.push(1);
            } else if(movement.player.id === players[1].id){
                row.push(2);
            } else {
                row.push(0);
            }
          }
          generatedboard.push(row);
        }
        setBoard(generatedboard);
    }


  useEffect(async ()=> {

    const game = await getGameById(gameId);
    console.log(game)
    // En caso de que no haya movimientos, comprobar si eres el primer player 
    // en caso de haber movimientos, comprobar que el ultimo movimiento no es tuyo
    // en cualquiera de los dos casos, setear a true 
    setData({
      players: game.players,
      movements: game.movements,
      winner: game.winner,
      finished: game.finished
    })
    setMovementEventHandler(receiveMovement);
    initBoard(game.movements, game.players);
  }, [])

 

  const putPiece = (columnIndex) => {
    console.log(isPlayerTurn)
    if(!isPlayerTurn){
      return 
    }
    let r=5; 
    for(r; r >= 0; r--){
      if(board[r][columnIndex] == 0){
        break;
      }
    }
    if(r < 0){
      errorNotification("Error puting piece", "Column is full",'topRight');
      return ;
    }

    const updateData = {...data, movements: [...data.movements, {row: r, col: columnIndex, player:{ id :parseInt(localStorage.getItem("playerId"))}}]};
    setData(updateData);
    initBoard(updateData.movements, updateData.players);
    
    const movement  = {
      row: r,
      col: columnIndex,
      playerId: localStorage.getItem("playerId"),
      gameId: gameId
    };
    setIsPlayerTurn(false);
    sendEvent('/make-movement', movement);
  }
 

  const receiveMovement = (movementReceived) => {
  if(movementReceived.playerId == localStorage.getItem("playerId")){
    return;
  }
      setData(prevState => {
          const updateData = {...prevState,movements: [...prevState.movements, {row: movementReceived.row, col: movementReceived.col, player:{ id: movementReceived.playerId}}]};
          initBoard(updateData.movements, updateData.players);
          setIsPlayerTurn(true);
            return updateData;
      });
  }

    if(data === {}){
        return <div>loading</div>
    }
    return (
        <div style={{padding: '50px'}}>
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
                  ) }}/>
          </Col>
          <Col span={18}> <table>
            <thead>
            </thead>
              <tbody>
                   {board.map((row, i) => (
            <GameRow key={i} row={row} putPiece={putPiece} isPlayerTurn= {isPlayerTurn} />))}
                    </tbody>
                    </table>
                    </Col>
                    </Row>
          <button onClick={() => console.log(data)}>PULSAME</button>
        </div>
    )
}
