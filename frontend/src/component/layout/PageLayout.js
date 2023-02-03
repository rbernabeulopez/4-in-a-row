import {Layout} from "antd";
import Header from "./Header";
const { Content, Footer } = Layout;

const PageLayout = ({ children }) => {
  return (
      <Layout className="layout">
          <Header />
          <Content style={{ padding: '50px', minHeight: 'calc(100vh - 64px - 70px)' }}>
                {children}
            </Content>
          <Footer style={{ textAlign: 'center' }}>Jackass ©2023</Footer>
        </Layout>
    );
};

export default PageLayout;