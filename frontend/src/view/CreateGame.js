import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Container, Row, Col } from "react-bootstrap";
import { makeSocketConnection } from "../request/webSocketRequest";
import { createGame } from "../request/gameRequest";
import { Historical } from "../component/Historical";
import {HistoryOutlined, LaptopOutlined, UserAddOutlined} from "@ant-design/icons";
import Title from "antd/es/skeleton/Title";
import {Button} from "antd";

const Section = ({ title, description, buttonText, handleClick, Icon }) => {
  return (
      <div style={{ background: '#fff', padding: 24, textAlign: 'center' }}>
        <Icon style={{ fontSize: '36px' }} />
        <Title level={1}>{title}</Title>
        <p>{description}</p>
        <Button type="primary" onClick={handleClick}>{buttonText}</Button>
      </div>
  )
}

export const CreateGame = () => {
  const navigate = useNavigate();

  const handleSubmit = async () => {
    let playerId = localStorage.getItem("playerId");
    let gameId = await createGame(playerId);
    navigate(`/table/${gameId}`)
  };

  const sections = [
    {
      title: "Create Game",
      description: "Start a new session with a friend",
      buttonText: "Go to Game",
      handleClick: () => handleSubmit(),
      Icon: LaptopOutlined
    },
    {
      title: "Join Game",
      description: "Join an existing session",
      buttonText: "Join Game",
      handleClick: () => navigate("/join-game"),
      Icon: UserAddOutlined
    },
    {
      title: "Historic",
      description: "See your historic games",
      buttonText: "Historic",
      handleClick: () => navigate("/historical"),
      Icon: HistoryOutlined
    }];

  return (
      <Row justify="center">
        {
          sections.map((section, index) => (
              <>
                <Col key={index} span={24 / sections.length}>
                  <Section {...section} />
                </Col>
              </>
          ))
        }
      </Row>
  );
};
