import React, { useEffect, useState } from "react";
import {makeSocketConnection} from "../../request/webSocketRequest";
import './table.css'
import {Col, List, Row} from "antd";
import GameRow from "../../component/GameRow";

export const Table =() => {
    useEffect(() => {
        makeSocketConnection(1, localStorage.getItem('playerId'));
    }, []);

    const [player, setPlayer] = useState(true);

    const [movement, setMovement] = useState({
      row: 0,
      col: 0,
      player_id: 0,
    });

    const [movementWS, setMovementWS] = useState({
      row: 3,
      col: 3, 
      player_id: 1, 
    });


    const [data, setData] = useState({
        players: [{
            id: 0,
            player_name: 'Alex',
        }, {
            id: 1,
            player_name: 'Pablo',
        }],
        movements: [
            {
                row: 5,
                col: 0,
                player_id: 0,
            },
            {
                row: 5,
                col: 1,
                player_id: 1,
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
    }


  useEffect(()=> {
    initBoard(data.movements, data.players);
  }, [])

  const putPiece = (columnIndex) => {
    setMovement({...movement, col: columnIndex, player});
    // send to websocket endpoint
    
  }

  function receiveMovement(movementWS) {
    // receive call from websocket
    const updateData = {...data,movements: [...data.movements, movementWS]};
    setData(updateData);
    console.log(updateData);
    initBoard(updateData.movements, updateData.players);
  }

    return (
        <div style={{padding: '50px'}}>
            <Row>
                <h1>Connect 4</h1>
            </Row>
            <Row>
                <Col span={6}>
                    <List
                        header={<b>Players</b>}
                        bordered
                        style={{ width: 300, marginTop: 20 }}
                        dataSource={data.players}
                        renderItem={(item) => { return (<List.Item>{item.player_name}</List.Item>) }}
                    />
                </Col>
                <Col span={18}>
                    <table>
                        <thead>
                        </thead>
                        <tbody>
                        {board.map((row, i) => (<GameRow key={i} row={row} putPiece={putPiece} />))}
                        </tbody>
                    </table>
                </Col>
            </Row>
        </div>
      );
}
