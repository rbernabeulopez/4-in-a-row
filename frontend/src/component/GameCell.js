import React from "react";

const GameCell = ({ value, columnIndex, putPiece, isPlayerTurn }) => {
    let color = 'white';
    if (value === 1) {
        color = 'red';
    } else if (value === 2) {
        color = 'yellow';
    }
    return (
        <td>
            <div className="cell" onClick={() => putPiece(columnIndex)} style={{cursor: isPlayerTurn ? "pointer": undefined}}>
                <div className={color}></div>
            </div>
        </td>
    );
};

export default GameCell;