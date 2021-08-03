import {AutoComplete, Avatar, Badge, Input, Menu, Popover, Tooltip} from "antd";
import {BellOutlined, BookOutlined, SearchOutlined} from "@ant-design/icons";
import Notice from "./Notice";
import avatar from "../assets/avatar.png";
import {Link, useHistory} from "react-router-dom";
import React, {FC, useContext, useState} from "react";

import "./HeaderMenu.css";
import userContext from "../context/userContext";
import {queryMenuList} from "../services/menu";

const {SubMenu} = Menu;

const HeaderMenu: FC = () => {
    const history = useHistory();

    const context = useContext(userContext);
    const [menuOptions, setMenuOptions] = useState<{ label: string, value: string, path: string }[]>([]);

    const onLogout = () => {
        localStorage.clear();
        history.replace('/login');
    };

    const onSearchMenu = async (keyword: string) => {
        if (!keyword) {
            setMenuOptions([]);
            return;
        }

        const menus = await queryMenuList(keyword);
        setMenuOptions(menus.map(menu => {
            return {
                label: menu.menuName as string,
                value: menu.menuName as string,
                path: menu.menuPath as string
            }
        }));
    };

    return (
        <Menu theme="light" mode="horizontal" className="header-menu">
            <Menu.Item key="search">
                <AutoComplete
                    style={{width: 200}}
                    autoFocus={true}
                    allowClear={true}
                    backfill={false}
                    options={menuOptions}
                    onSearch={onSearchMenu}
                    onSelect={(value, option) => history.push(option.path)}
                >
                    <Input bordered={false} placeholder="搜索" prefix={<SearchOutlined/>}/>
                </AutoComplete>
            </Menu.Item>

            <Menu.Item key="notice">
                <Popover trigger="click" placement="bottomRight" content={<Notice/>}>
                    <Badge count={12}><BellOutlined style={{fontSize: 18, border: 0}}/></Badge>
                </Popover>
            </Menu.Item>

            <SubMenu key="avatar" icon={
                <>
                    <Avatar src={avatar} className="avatar"/>
                    <span className="avatar-text">{context.name}</span>
                </>
            }>
                <Menu.Item key="center">
                    <Link to='/personal/center'>个人中心</Link>
                </Menu.Item>
                <Menu.Item key="settings">
                    <Link to='/personal/settings'>个人设置</Link>
                </Menu.Item>
                <Menu.Divider/>
                <Menu.Item key="logout">
                    <a onClick={onLogout}>退出登录</a>
                </Menu.Item>
            </SubMenu>

            <Menu.Item key="knowledge">
                <Tooltip title="技术文档" color="blue">
                    <a onClick={() => {
                        window.open('https://www.yuque.com/qingtian-skp6f/xtgdy7/gl89qv')
                    }} target="_blank">
                        <BookOutlined/>
                    </a>
                </Tooltip>
            </Menu.Item>
        </Menu>
    )
};

export default HeaderMenu;