import {Layout, Row, Col, Button} from 'antd';
import {Link, useNavigate} from 'react-router-dom';

const Header = () => {
    const navigate = useNavigate()

    function logout () {
        localStorage.removeItem('playerToken');
        localStorage.removeItem('playerName');
        localStorage.removeItem('playerId');
        navigate('/login')
    }

    return (
        <Layout.Header>
            <Row justify="space-between">
                <Col>
                    <Link to="/">
                        <img src="/logo.png" alt="Logo" style={{ height: '30px', margin: '20px 0px', color: 'white' }} />
                        <span style={{ color: 'white', fontSize: '20px', fontWeight: 'bold', margin: '20px 10px' }}>
                            4 In A Row
                        </span>
                    </Link>
                </Col>
                <Col>
                    {
                        !localStorage.getItem('playerToken') ?
                            <>
                                <Link to="/login">
                                    <Button type="primary" style={{ marginRight: '10px' }}>Login</Button>
                                </Link>
                                <Link to="/register">
                                    <Button type="primary">Register</Button>
                                </Link>
                            </> :
                            <>
                                <span style={{color: 'white', marginRight: "10px"}}>
                                    Welcome {localStorage.getItem('playerName')}
                                </span>
                                <Button type="primary" onClick={logout}>Logout</Button>
                            </>
                    }
                </Col>
            </Row>
        </Layout.Header>
    );
};

export default Header;