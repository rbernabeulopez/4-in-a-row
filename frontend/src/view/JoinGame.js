import {Button, Card, Form, Input} from "antd";

const JoinGame = () => {

    return (
        <div style={{ display: "flex", justifyContent: "center", alignItems: "center" }}>
            <h1>Join Game</h1>
            <Card style={{ width: 300 }}>
                <Form onFinish={console.log}>
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