import React, {FC, useState} from 'react';
import {renderRoutes} from 'react-router-config';
import {Link} from "react-router-dom";
import {Affix, Avatar, Badge, Breadcrumb, Input, Layout, Menu, Popover, Tooltip} from "antd";
import {
    AreaChartOutlined,
    BellOutlined,
    BookOutlined,
    DatabaseOutlined,
    DesktopOutlined,
    FileImageOutlined,
    MenuFoldOutlined,
    MenuUnfoldOutlined,
    PictureOutlined,
    ReadOutlined,
    SearchOutlined,
    TeamOutlined
} from "@ant-design/icons";
import Notice from "./Notice";

import './BasicLayout.css';
import avatar from '../assets/avatar.png';
import logo from '../assets/logo.svg';

const {Sider, Header, Content} = Layout;
const {SubMenu} = Menu;

const BasicLayout: FC = (props) => {
    const [collapsed, setCollapsed] = useState<boolean>(false);

    return (
        <Layout id="root">
            <Sider trigger={null} collapsible collapsed={collapsed}>
                <div className="left">
                    <div className="logo">
                        <a>
                            <img src={logo} alt="logo"/>
                            <h1 style={collapsed ? {display: "none"} : {display: "inline-block"}}>One Piece</h1>
                        </a>
                    </div>

                    <Menu theme="dark" mode="inline" defaultSelectedKeys={['workbench']}>
                        <Menu.Item key="workbench" icon={<DesktopOutlined/>}>
                            <Link to='/workbench'>工作台</Link>
                        </Menu.Item>
                        <SubMenu key="admin" icon={<TeamOutlined/>} title="管理中心">
                            <Menu.Item key="organization">
                                <Link to='/admin/organization'>组织管理</Link>
                            </Menu.Item>
                            <Menu.Item key="user">
                                <Link to='/admin/user'>用户管理</Link>
                            </Menu.Item>
                            <Menu.Item key="role">
                                <Link to='/admin/role'>角色管理</Link>
                            </Menu.Item>
                            <Menu.Item key="resource">
                                <Link to='/admin/resourceCategory'>资源管理</Link>
                            </Menu.Item>
                            <Menu.Item key="menu">
                                <Link to='/admin/menu'>菜单管理</Link>
                            </Menu.Item>
                        </SubMenu>
                        <Menu.Item key="statistics" icon={<AreaChartOutlined/>}>
                            <Link to='/statistics'>统计中心</Link>
                        </Menu.Item>
                        <Menu.Item key="logging" icon={<DatabaseOutlined/>}>
                            <Link to='/logging'>日志中心</Link>
                        </Menu.Item>
                        <Menu.Item key="monitor" icon={<PictureOutlined/>}>
                            <Link to='/monitor'>监控中心</Link>
                        </Menu.Item>
                        <Menu.Item key="attachment" icon={<FileImageOutlined/>}>
                            <Link to='/attachment'>附件中心</Link>
                        </Menu.Item>
                        <Menu.Item key="document" icon={<ReadOutlined/>}>
                            <Link to='/document'>文档中心</Link>
                        </Menu.Item>
                    </Menu>
                </div>
            </Sider>

            <Layout className="main">
                <Affix>
                    <Header className="header">
                        {
                            React.createElement(collapsed ? MenuUnfoldOutlined : MenuFoldOutlined, {
                                className: 'trigger',
                                onClick: () => setCollapsed(!collapsed),
                            })
                        }

                        <Breadcrumb className="breadcrumb">
                            <Breadcrumb.Item>管理中心</Breadcrumb.Item>
                            <Breadcrumb.Item>用户管理</Breadcrumb.Item>
                        </Breadcrumb>

                        <Menu theme="light" mode="horizontal" className="menu">
                            <Menu.Item key="search" className="search">
                                <Input prefix={<SearchOutlined/>} placeholder="搜索" bordered={false}/>
                            </Menu.Item>

                            <Menu.Item key="notice">
                                <Popover trigger="click" placement="bottomRight" content={<Notice/>}>
                                    <Badge count={12}><BellOutlined style={{fontSize: 18, border: 0}}/></Badge>
                                </Popover>
                            </Menu.Item>

                            <SubMenu key="avatar" icon={
                                <>
                                    <Avatar src={avatar} className="avatar"/>
                                    <span className="avatar-text">管理员</span>
                                </>
                            }>
                                <Menu.Item key="center">
                                    <Link to='/center'>个人中心</Link>
                                </Menu.Item>
                                <Menu.Item key="settings">
                                    <Link to='/settings'>个人设置</Link>
                                </Menu.Item>
                                <Menu.Divider/>
                                <Menu.Item key="logout">
                                    <Link to='/logout'>退出登录</Link>
                                </Menu.Item>
                            </SubMenu>

                            <Menu.Item key="knowledge">
                                <Tooltip title="开发文档" color="blue">
                                    <Link to='/knowledge'><BookOutlined/></Link>
                                </Tooltip>
                            </Menu.Item>
                        </Menu>
                    </Header>
                </Affix>
                <Content className="content">
                    {
                        // @ts-ignore
                        renderRoutes(props.route.routes)
                    }
                </Content>
            </Layout>
        </Layout>
    );
}

export default BasicLayout;