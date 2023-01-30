import React, { useEffect, useState} from "react";
import { useParams } from "react-router-dom";
import {makeSocketConnection, setMovementEventHandler} from "../../request/webSocketRequest";
import './table.css'
import {Col, List, Row} from "antd";
import GameRow from "../../component/GameRow";
import { sendEvent } from "../../request/webSocketRequest";
import { errorNotification } from "../../util/notification";

const i = 0;


export const Table =() => {


  const { gameId } = useParams();
  
  useEffect(() => {
    let playerId = localStorage.getItem("playerId")
    makeSocketConnection(gameId, playerId);
  }, [])
  
  /*
  const registerPlayer = () => {
    let Sock = new SockJS('hhtp://locahost:8080/service/v1');
    stompClient = over(Sock);
    stompClient.connect({}, onConnected, onError);
  }

  const onConnected = () => {
    stompClient.subscribe('/game-notifications/1')
    console.log("mensaje de vuelta");
  }

  const onError = (err) => {
    console.log(err)
  }
  */

    const [isPlayerTurn, setIsPlayerTurn] = useState(true);

    const [data, setData] = useState({
        players: [{
            id: 1,
            player_name: 'Alex',
        }, {
            id: 2,
            player_name: 'Pablo',
        }],
        movements: [
            {
                row: 5,
                col: 0,
                player_id: 1,
            },
            {
                row: 5,
                col: 1,
                player_id: 2,
            }
            
        ],
        winner: null,
        finished: false
    })

    const [board, setBoard] = useState([]);


    // Starts new game
    const initBoard= (movements, players) => {
        // Create a blank 6x7 matrix
        let generatedboard = [];
        for (let r = 0; r < 6; r++) {
          let row = [];
          for (let c = 0; c < 7; c++) {
            const movement = movements.find((movement) => movement.row === r && movement.col === c);
            if(movement == null){
                row.push(0);
            } else if(movement.player_id === players[0].id){
                row.push(1);
            } else if(movement.player_id === players[1].id){
                row.push(2);
            } else {
                row.push(0);
            }
          }
          generatedboard.push(row);
        }
        setBoard(generatedboard);
        //registerPlayer();
    }


  useEffect(()=> {
    setMovementEventHandler(receiveMovement);
    initBoard(data.movements, data.players);
  }, [])



  const putPiece = (columnIndex) => {
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

    const updateData = {...data, movements: [...data.movements, {row: r, col: columnIndex, player_id: parseInt(localStorage.getItem("playerId"))}]};
    setData(updateData);
    initBoard(updateData.movements, updateData.players);
    
    const movement  = {
      row: r,
      col: columnIndex,
      playerId: localStorage.getItem("playerId"),
      gameId: gameId
    };

    sendEvent('/make-movement', movement);
    setIsPlayerTurn(!isPlayerTurn);  
  }
 

  function receiveMovement(movementReceived) {
    
    if(movementReceived.playerId == localStorage.getItem("playerId")){
      return;
    }
    
    const updateData = {...data,movements: [...data.movements, {row: movementReceived.row, col: movementReceived.col, player_id: movementReceived.playerId}]};
    console.log(data.movements)
    setData(updateData);
    initBoard(updateData.movements, updateData.players);
    setIsPlayerTurn(isPlayerTurn);
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
              <List.Item>{item.player_name}</List.Item>
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
    </div>
    )
}
