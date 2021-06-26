import React, {FC, useState} from 'react';
import {Avatar, Badge, Input, Layout, Menu, Popover, Tabs, List, Breadcrumb} from 'antd';
import {
    AreaChartOutlined,
    BellOutlined,
    DatabaseOutlined,
    DesktopOutlined,
    FileImageOutlined,
    MenuFoldOutlined,
    MenuUnfoldOutlined,
    PictureOutlined,
    QuestionCircleOutlined,
    ReadOutlined,
    SearchOutlined,
    TeamOutlined,
} from '@ant-design/icons';

import './App.css';

const {Header, Sider, Content} = Layout;
const {SubMenu} = Menu;
const {TabPane} = Tabs;

const App: FC = () => {
    const [collapsed, setCollapsed] = useState<boolean>(false);

    const info = <Tabs defaultActiveKey="notice">
        <TabPane tab="通知" key="notice" className="info">
            <List
                itemLayout="horizontal"
                dataSource={[
                    {
                        title: 'Ant Design Title 1',
                    },
                    {
                        title: 'Ant Design Title 2',
                    },
                    {
                        title: 'Ant Design Title 3',
                    },
                    {
                        title: 'Ant Design Title 4',
                    },
                ]}
                renderItem={item => (
                    <List.Item>
                        <List.Item.Meta
                            avatar={<Avatar src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png" />}
                            title={<a href="https://ant.design">{item.title}</a>}
                            description="Ant Design, a design language for background applications, is refined by Ant UED Team"
                        />
                    </List.Item>
                )}
            />
        </TabPane>
        <TabPane tab="消息" key="message">
            Content of Tab Pane 2
        </TabPane>
        <TabPane tab="代办" key="todo">
            Content of Tab Pane 3
        </TabPane>
    </Tabs>;

    return (
        <Layout id="root">
            <Sider trigger={null} collapsible collapsed={collapsed}>
                <div className="logo">
                    <a>
                        <img src="logo.svg" alt="logo"/>
                        <h1 style={collapsed ? {display: "none"} : {display: "inline-block"}}>OnePiece</h1>
                    </a>
                </div>
                <Menu theme="dark" mode="inline" defaultSelectedKeys={['workbench']}>
                    <Menu.Item key="workbench" icon={<DesktopOutlined/>}>
                        工作台
                    </Menu.Item>
                    <SubMenu key="management" icon={<TeamOutlined/>} title="管理中心">
                        <Menu.Item key="organization">
                            组织管理
                        </Menu.Item>
                        <Menu.Item key="user">
                            用户管理
                        </Menu.Item>
                        <Menu.Item key="role">
                            角色管理
                        </Menu.Item>
                        <Menu.Item key="resource">
                            资源管理
                        </Menu.Item>
                        <Menu.Item key="menu">
                            菜单管理
                        </Menu.Item>
                    </SubMenu>
                    <Menu.Item key="statistics" icon={<AreaChartOutlined/>}>
                        统计中心
                    </Menu.Item>
                    <Menu.Item key="logging" icon={<DatabaseOutlined/>}>
                        日志中心
                    </Menu.Item>
                    <Menu.Item key="monitor" icon={<PictureOutlined/>}>
                        监控中心
                    </Menu.Item>
                    <Menu.Item key="file" icon={<FileImageOutlined/>}>
                        附件中心
                    </Menu.Item>
                    <Menu.Item key="document" icon={<ReadOutlined/>}>
                        文档中心
                    </Menu.Item>
                </Menu>
            </Sider>
            <Layout className="main">
                <Header className="header">
                    {
                        React.createElement(collapsed ? MenuUnfoldOutlined : MenuFoldOutlined, {
                            className: 'trigger',
                            onClick: () => setCollapsed(!collapsed),
                        })
                    }
                    <Menu theme="light" mode="horizontal" style={{float: "right", right: 0, width: 500}}>
                        <Menu.Item key="search">
                            <Input prefix={<SearchOutlined/>} placeholder="搜索" bordered={false}/>
                        </Menu.Item>
                        <Popover trigger="click" placement="bottomRight" content={info} >
                            <Menu.Item key="info">
                                    <Badge count={12}><BellOutlined style={{fontSize: 18, border: 0}}/></Badge>
                            </Menu.Item>
                        </Popover>

                        <SubMenu key="avatar" icon={
                            <>
                                <Avatar src="avatar.png"
                                        style={{width: 24, height: 24, position: "relative", top: -3}}/>
                                <span style={{marginLeft: 6, fontSize: 14}}>李萌</span>
                            </>
                        }>
                            <Menu.Item key="center">
                                个人中心
                            </Menu.Item>
                            <Menu.Item key="settings">
                                个人设置
                            </Menu.Item>
                            <Menu.Divider/>
                            <Menu.Item key="logout">
                                退出登录
                            </Menu.Item>
                        </SubMenu>
                        <Menu.Item key="development">
                            <QuestionCircleOutlined title="开发文档"/>
                        </Menu.Item>
                    </Menu>
                </Header>
                <Content className="content">
                    <Breadcrumb>
                        <Breadcrumb.Item>管理中心</Breadcrumb.Item>
                        <Breadcrumb.Item>用户管理</Breadcrumb.Item>
                    </Breadcrumb>
                    Content
                </Content>
            </Layout>
        </Layout>
    );
}

export default App;