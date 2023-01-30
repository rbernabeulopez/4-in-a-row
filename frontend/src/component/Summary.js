import axios from "axios";
import React, { useEffect, useState } from "react";
import {Button, Col, Container, Row, Table} from 'react-bootstrap';

export const Summary = () => {

    //aqui metemos el id de la partida
    const [id, setId] = useState();

    //aqui meteremos los datos que recogemos de la base de datos
    const [data, setData] = useState([]);

    //datos de prueba
    const datos = [
        {
          id: 1,
          winner: {
            id: 1,
            name: "Pablo"
          },
          players: [
            {
              id: 1,
              name: "Pablo"
            },
            {
              id: 2,
              name: "Chuchi"
            }
          ],
          finished: true
          /*
          movements: [
            {
                id: 1,
                player:{
                    id: 1,
                    name: "Pablo",
                    },
                row: 3,
                col: 1,
            }
          ],
          */
        },
      ]



    //cuando tengamos el id con este useEffect hacemos una llamada para obtener 
    //toda la información de la partida
    useEffect(() => {

        axios({
            url: "/api/v1/1",
            method: "GET",
        })
        .then((res) => {
            setData(res.data)
        })
        .catch((err) => {
            console.log(err);
        })
    }, []);


    return(
        <Container>
        <h1>Historico de partidas</h1>
            <Row>
                <Col>
                    <Table striped bordered hover variant="dark">
                        <thead>
                            <tr>
                            <th>Ganador: {datos.winner.name}</th>
                            <th>Estatus: {datos.finished}</th>
                            </tr>
                        </thead>
                        <tbody>
                                {datos.map((elemento)=>(
                                    <tr>
                                    <td>{elemento}</td>
                                    </tr>
                                )
                                )}
                        </tbody>
                    </Table>
                </Col>
            </Row>
        </Container>
    );
}