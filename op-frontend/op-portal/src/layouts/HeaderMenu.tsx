import {Avatar, Badge, Input, Menu, Popover, Tooltip} from "antd";
import {BellOutlined, BookOutlined, SearchOutlined} from "@ant-design/icons";
import Notice from "./Notice";
import avatar from "../assets/avatar.png";
import {Link} from "react-router-dom";
import React, {FC, useContext} from "react";

import "./HeaderMenu.css";
import UserContext from "../context/userContext";

const {SubMenu} = Menu;

const HeaderMenu: FC = () => {
    const {username} = useContext(UserContext);

    return (
        <Menu theme="light" mode="horizontal" className="header-menu">
            <Menu.Item key="search">
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
                    <span className="avatar-text">{username}</span>
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
    )
};

export default HeaderMenu;