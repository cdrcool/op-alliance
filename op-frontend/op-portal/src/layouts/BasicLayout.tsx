import React, {FC, useState} from 'react';
import {renderRoutes, matchRoutes} from 'react-router-config';
import {Link} from "react-router-dom";
import {Affix, Avatar, Badge, Breadcrumb, Input, Layout, Menu, Popover, Tooltip} from "antd";
import {AreaChartOutlined, BellOutlined, BookOutlined, DatabaseOutlined, DesktopOutlined, FileImageOutlined,
    MenuFoldOutlined, MenuUnfoldOutlined, PictureOutlined, ReadOutlined, SearchOutlined, TeamOutlined} from "@ant-design/icons";
import Notice from "./Notice";

import './BasicLayout.css';

const {Sider, Header, Content} = Layout;
const {SubMenu} = Menu;

const BasicLayout: FC = (props) => {
    const [collapsed, setCollapsed] = useState<boolean>(false);

    return (
        <Layout id="root">
            <Sider trigger={null} collapsible collapsed={collapsed}>
                <div className="logo">
                    <a>
                        <img src="../logo.svg" alt="logo"/>
                        <h1 style={collapsed ? {display: "none"} : {display: "inline-block"}}>OnePiece</h1>
                    </a>
                </div>

                <Menu theme="dark" mode="inline" defaultSelectedKeys={['workbench']}>
                    <Menu.Item key="workbench" icon={<DesktopOutlined/>}>
                        工作台
                    </Menu.Item>
                    <SubMenu key="management" icon={<TeamOutlined/>} title="管理中心">
                        <Menu.Item key="organization">
                            <Link to='/organization'>组织管理</Link>
                        </Menu.Item>
                        <Menu.Item key="user">
                            <Link to='/user'>用户管理</Link>
                        </Menu.Item>
                        <Menu.Item key="role">
                            <Link to='/role'>角色管理</Link>
                        </Menu.Item>
                        <Menu.Item key="resource">
                            <Link to='/resource'>资源管理</Link>
                        </Menu.Item>
                        <Menu.Item key="menu">
                            <Link to='/menu'>菜单管理</Link>
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
                    <Menu.Item key="file" icon={<FileImageOutlined/>}>
                        <Link to='/file'>附件中心</Link>
                    </Menu.Item>
                    <Menu.Item key="document" icon={<ReadOutlined/>}>
                        <Link to='/document'>文档中心</Link>
                    </Menu.Item>
                </Menu>
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

                            <Popover trigger="click" placement="bottomRight" content={<Notice/>}>
                                <Menu.Item key="notice">
                                    <Badge count={12}><BellOutlined style={{fontSize: 18, border: 0}}/></Badge>
                                </Menu.Item>
                            </Popover>

                            <SubMenu key="avatar" icon={
                                <>
                                    <Avatar src="avatar.png" className="avatar"/>
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

                            <Tooltip title="开发文档" color="blue">
                                <Menu.Item key="knowledge">
                                    <Link to='/knowledge'><BookOutlined/></Link>
                                </Menu.Item>
                            </Tooltip>
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