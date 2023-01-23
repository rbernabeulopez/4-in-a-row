import React, { useEffect, useState } from "react";

export const Table =() => {

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
    const initBoard= () => {
        // Create a blank 6x7 matrix
        let generatedboard = [];
        for (let r = 0; r < 6; r++) {
          let row = [];
          // If movement = player 1, else player 2
          for (let c = 0; c < 7; c++) {
            console.log(r,c);
            const movement = data.movements.find((movement) => movement.row === r && movement.col === c);
            console.log(movement, data.players);
            if(movement == null){
                row.push(0);
            } else if(movement.player_id == data.players[0].id){
                row.push(1);
            } else if(movement.player_id == data.players[1].id){
                row.push(2);
            } else {
                row.push(0);
            }
           
            }
          generatedboard.push(row);
        }
        setBoard(generatedboard);
    }



 const Row = ({ row }) => {
    return (
      <tr>
        {row.map((cell, i) => <Cell key={i} value={cell} columnIndex={i} />)}
      </tr>
    );
  };
  
  const Cell = ({ value, columnIndex }) => {
    let color = 'white';
    if (value === 1) {
      color = 'red';
    } else if (value === 2) {
      color = 'yellow';
    }
      
    return (
      <td>
        <div className="cell" onClick={() => {console.log("cell")}}>
          <div className={color}></div>
        </div>
      </td>
    );
  };


  useEffect(()=> {
    initBoard();
  }, [])

  const putPiece = () => {

  }

    return (
        <div>
          
          <table>
            <thead>
            </thead>
            <tbody>
            {board.map((row, i) => (<Row key={i} row={row} />))}
            </tbody>
          </table>
        </div>
      );
    }
