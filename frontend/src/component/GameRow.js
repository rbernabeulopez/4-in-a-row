import React from "react";
import GameCell from "./GameCell";

const GameRow = ({ row, putPiece }) => {
    return (
        <tr>
            {row.map((cell, i) => <GameCell key={i} value={cell} columnIndex={i} putPiece={putPiece} />)}
        </tr>
    );
};

export default GameRow;