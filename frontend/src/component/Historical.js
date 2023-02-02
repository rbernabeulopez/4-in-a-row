import axios from "axios";
import React, { useEffect, useState } from "react";
import {Button, Col, Container, Row, Table} from 'react-bootstrap';
// import { Table } from 'antd'

export const Historical = () => {

    //aqui meteremos los datos de la base de datos
    const [data, setData] = useState([]);

    
    const datos = [
        {id: 1, name: "Pablo", estatus: "Ganador"},
        {id: 2, name: "Pablo", estatus: "Ganador"},
        {id: 3, name: "Pablo", estatus: "Empate"}
    ]


    const dataSource = [
        {
          key: '1',
          name: 'Mike',
          age: 32,
          address: '10 Downing Street',
        },
        {
          key: '2',
          name: 'John',
          age: 42,
          address: '10 Downing Street',
        },
      ];
      
      const columns = [
        {
          title: 'Name',
          dataIndex: 'name',
          key: 'name',
        },
        {
          title: 'Age',
          dataIndex: 'age',
          key: 'age',
        },
        {
          title: 'Address',
          dataIndex: 'address',
          key: 'address',
        },
      ];



    // la magia del asunto está en como nos traemo el id

    useEffect(() => {
      let number = localStorage.getItem("playerId");

        console.log(number)

        axios({
            url:`/api/v1/player/${number}/games`,
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

    //tengo que traerme el id del jugador para enviarlo por el use efect

    return(
        <Container>
            <h1>Historico de partidas</h1>
            <Row>
                <Col>

                <Table striped bordered hover variant="dark">
                    <thead>
                        <tr>
                        <th>Nombre</th>
                        <th>Id partida</th>
                        <th>Estatus</th>
                        </tr>
                    </thead>
                    <tbody>
                            {datos.map((elemento)=>(
                                 <tr>
                                 <td>{elemento.name}</td>
                                 <td>{elemento.id}</td>
                                 <td>{elemento.estatus}</td>
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