import {Button, Form, Input} from "antd";
import {LockFilled, UserOutlined} from "@ant-design/icons";
import '../styles/form.css'

const FormPlayerData = ({handleSubmit, formName, extraElements}) => {
    return (
        <div className="user-data-container">
            <div className="user-data-title">
                <h2>{formName}</h2>
            </div>
            <Form onFinish={handleSubmit} className="user-data-form">
                <Form.Item name="username" rules={[
                    {
                        required: true,
                        message: "Please input your username!"
                    }
                ]}>
                    <Input prefix={<UserOutlined className="site-form-item-icon"/>} placeholder="Username"/>
                </Form.Item>
                <Form.Item name="password" rules={[
                    {
                        required: true,
                        message: "Please input your password!"
                    }
                ]}>
                    <Input.Password prefix={<LockFilled className="site-form-item-icon"/>} placeholder="Password"/>
                </Form.Item>
                <Form.Item>
                    <Button type="primary" htmlType="submit" className="user-data-form-button">
                        {formName}
                    </Button>
                </Form.Item>
                {extraElements}
            </Form>
        </div>
    );
}

export default FormPlayerData;