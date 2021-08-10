import React, {FC, useEffect, useState} from "react";
import type {MenuDataItem, ProSettings} from "@ant-design/pro-layout";
import ProLayout, {SettingDrawer} from "@ant-design/pro-layout";
import * as Icon from "@ant-design/icons";
import {MenuFoldOutlined, MenuUnfoldOutlined} from "@ant-design/icons";
import "./DefaultLayout.css";
import logo from "../assets/logo.svg";
import {Link, useHistory, useLocation} from "react-router-dom";
import {renderRoutes} from "react-router-config";
import defaultSettings from "./defaultSettings";
import HeaderMenu from "./HeaderMenu";
import {queryUserMenuTreeList} from "../services/menu";
import {Menus} from "../models/Menus";

const DefaultLayout: FC = (props) => {
    const history = useHistory();

    const [settings, setSetting] = useState<Partial<ProSettings> | undefined>(defaultSettings);
    const location = useLocation();

    const [collapsed, setCollapsed] = useState<boolean>(false);

    const convertToAntdMenu = (menus: Menus[]): MenuDataItem[] => {
        return menus && menus.map(item => {
            return {
                name: item.menuName,
                path: item.menuPath,
                icon: item.menuIcon && React.createElement(
                    // @ts-ignore
                    Icon[item.menuIcon],
                    {
                        style: {fontSize: '16px'}
                    }
                ),
                hideInMenu: !item.isShow,
                children: item.children && convertToAntdMenu(item.children),
            }
        });
    }

    useEffect(() => {
        const accessToken = localStorage.getItem('accessToken');
        if (!accessToken) {
            history.push('/login');
        }
    }, []);

    return (
        <div id="default-layout">
            <ProLayout
                logo={logo}
                location={{
                    pathname: location.pathname,
                }}
                menu={{
                    request: async () => {
                        const result = await queryUserMenuTreeList();
                        return convertToAntdMenu(result);
                    }
                }}
                headerContentRender={() => {
                    return (
                        <div
                            onClick={() => setCollapsed(!collapsed)}
                            style={{
                                cursor: 'pointer',
                                fontSize: '16px',
                            }}
                        >
                            {collapsed ? <MenuUnfoldOutlined/> : <MenuFoldOutlined/>}
                        </div>
                    );
                }}
                collapsed={collapsed}
                onCollapse={setCollapsed}
                collapsedButtonRender={false}
                menuHeaderRender={(logo, title) => (
                    <div
                        onClick={() => {
                            window.open('https://remaxjs.org/');
                        }}
                    >
                        {logo}
                        {title}
                    </div>
                )}
                onMenuHeaderClick={(e) => console.log(e)}
                menuItemRender={
                    (menuDataItem, dom) => {
                        if (!menuDataItem.path) {
                            return dom;
                        }
                        return <Link to={menuDataItem.path}>{dom}</Link>;
                    }
                }
                rightContentRender={() => <HeaderMenu/>}
                {...settings}
            >
                {
                    // @ts-ignore
                    renderRoutes(props.route.routes)
                }

            </ProLayout>
            <SettingDrawer
                pathname={location.pathname}
                getContainer={() => document.getElementById('default-layout')}
                settings={settings}
                onSettingChange={(changeSetting) => {
                    setSetting(changeSetting);
                }}
                disableUrlParams
            />
        </div>
    );
};

export default DefaultLayout;