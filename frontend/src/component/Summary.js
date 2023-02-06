import axios from "axios";
import React, { useEffect, useState } from "react";
import { Col, Container, Row, Table } from 'react-bootstrap';
import { useParams } from "react-router-dom";
import GameRow from "../component/GameRow";

export const Summary = () => {

    const { gameId } = useParams();

    const [board, setBoard] = useState([]);

    useEffect(() => {

        axios({
            url: `/api/v1/game/${gameId}`,
            method: "GET",
        })
            .then((res) => {
                console.log("Recibido resumen")
                console.log(res);
                initBoard(res.data.movements, res.data.players)
            })
            .catch((err) => {
                console.log(err);
            })
    }, []);


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


    return (
        <Container>
            <h1>Resumen de la partida</h1>
            <Row>
                <Col span={18}> <table>
                    <thead>
                    </thead>
                    <tbody>
                        {board.map((row, i) => (
                            <GameRow key={i} row={row} />))}
                    </tbody>
                </table>
                </Col>
            </Row>
        </Container>
    );
}