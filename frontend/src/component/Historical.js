import axios from "axios";
import React, { useEffect, useState } from "react";
import {Col, Container, Row, Table} from 'react-bootstrap';
import { useNavigate } from "react-router-dom";

export const Historical = () => {

    const [data, setData] = useState([]);

    const navigate = useNavigate();

    useEffect(() => {
      let playerId = localStorage.getItem("playerId");

        console.log(playerId)

        axios({
            url:`/api/v1/player/${playerId}/games`,
            method: "GET",
        })
        .then((res) => {
          console.log("Recibido historial")
            setData(res.data)
        })
        .catch((err) => {
            console.log(err);
        })
    }, []);

    const handleClick = (gameId) => {
        navigate(`/summary/${gameId}`)
    }

    return(
        <Container>
            <h1>Historico de partidas</h1>
            <Row>
                <Col>

                <Table striped bordered hover>
                    <thead>
                        <tr>
                          <th>Nombre</th>
                          <th>Estatus</th>
                          <th>Oponente</th>
                          <th>Más información de la partida</th>
                        </tr>
                    </thead>
                    <tbody>
                            {data.map((elemento)=>(
                                 <tr>
                                  <td>{localStorage.getItem("playerName")}</td>
                                  <td>{elemento.winner.id == localStorage.getItem("playerId") ? "Ganaste" : "Perdiste"}</td>
                                  <td>{elemento.players[0].id != localStorage.getItem("playerId") ? elemento.players[0].name : elemento.players[1].name }</td>
                                  <td><button onClick={() => handleClick(elemento.id)}>Ver</button></td>
                                </tr>
                              
                            )
                            )}
                        </tbody>
                    </Table>
                </Col>
            </Row>
        </Container>
    )
}