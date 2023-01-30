import React from "react";
import GameCell from "./GameCell";

const GameRow = ({ row, putPiece, isPlayerTurn}) => {
    return (
        <tr>
            {row.map((cell, i) => <GameCell key={i} value={cell} columnIndex={i} putPiece={putPiece} isPlayerTurn={isPlayerTurn}/>)}
        </tr>
    );
};

export default GameRow;