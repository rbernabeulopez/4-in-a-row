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

    const [isPlayerTurn, setIsPlayerTurn] = useState(true);

    const [data, setData] = useState({})

    const [board, setBoard] = useState([]);

    const [game, setGame] = useState();

    const oneGame = getGameById(gameId);

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
    }


  useEffect(()=> {
    const getGame = async(gameId) => {
      let newGame = await getGameById(gameId);
      console.log(newGame)
      setGame(newGame);
    }
    getGame(gameId);
    console.log(game);
    setData({
    //  players: [initialGame.players],
   //   movements: [initialGame.movements],
   //   winner: initialGame.winner,
   //   finished: initialGame.finished
    })
    setMovementEventHandler(receiveMovement);
    initBoard(data.movements, data.players);
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

    const updateData = {...data, movements: [...data.movements, {row: r, col: columnIndex, player_id: parseInt(localStorage.getItem("playerId"))}]};
    setData(updateData);
    initBoard(updateData.movements, updateData.players);
    console.log("data after put", updateData)
    
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
          const updateData = {...prevState,movements: [...prevState.movements, {row: movementReceived.row, col: movementReceived.col, player_id: movementReceived.playerId}]};
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
          <button onClick={() => console.log(data)}>PULSAME</button>
        </div>
    )
}
