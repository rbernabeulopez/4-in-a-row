import {Button, Card, Form, Input} from "antd";
import {joinGame} from "../request/gameRequest";
import {errorNotification} from "../util/notification";
import {useNavigate} from "react-router-dom";

const JoinGame = () => {

        const navigate = useNavigate();

    const handleJoinGame = (values) => {

        joinGame(values.gameId).then(() => {
            navigate(`/table/${values.gameId}`);
        }).catch((error) => {
            console.log(error, "?");
            errorNotification("Error joining game", error.response.data.message, "topRight");
        });

    };

    return (
        <div style={{ display: "flex", justifyContent: "center", alignItems: "center" }}>
            <h1>Join Game</h1>
            <Card style={{ width: 300 }}>
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
    )
}

export default JoinGame