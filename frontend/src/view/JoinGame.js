import {Button, Card, Form, Input} from "antd";
import {joinGame} from "../request/gameRequest";
import {errorNotification} from "../util/notification";
import {useNavigate} from "react-router-dom";
import { sendEvent } from "../request/webSocketRequest";

const JoinGame = () => {

        const navigate = useNavigate();

    const handleJoinGame = (values) => {

        joinGame(values.gameId).then(() => {
            navigate(`/table/${values.gameId}`);
            // sendEvent(`service/v1/table/${values.gameId}`)
        }).catch((error) => {
            const message = error.response?.data?.message || "Something went wrong";
            errorNotification("Error joining game", message, "topRight");
        });

    };

    return (
        <div style={{ display: "flex", justifyContent: "center", alignItems: "center" }}>
            <div className={"pt-5"}>
                <h1>Join Game</h1>
                <Card style={{ width: 300, marginTop: 16 }}>
                    <Form onFinish={handleJoinGame}>
                        <Form.Item label="Game ID" name="gameId">
                            <Input />
                        </Form.Item>
                        <Form.Item>
                            <Button type="primary" htmlType="submit">
                                Join Game
                            </Button>
                        </Form.Item>
                    </Form>
                </Card>
            </div>
        </div>
    )
}

export default JoinGame