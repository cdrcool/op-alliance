import React, {FC, useState} from "react";
import type {MenuDataItem, ProSettings} from "@ant-design/pro-layout";
import ProLayout, {SettingDrawer} from "@ant-design/pro-layout";
import {MenuUnfoldOutlined, MenuFoldOutlined} from "@ant-design/icons";
import "./DefaultLayout.css";
import logo from "../assets/logo.svg";
import {useLocation} from "react-router-dom";
import {renderRoutes} from "react-router-config";
import defaultSettings from "./defaultSettings";
import * as Icon from '@ant-design/icons'
import {Link} from "react-router-dom";
import HeaderMenu from "./HeaderMenu";
import {queryMenuTreeList} from "../services/menu";
import {Menus} from "../models/Menus";

const DefaultLayout: FC = (props) => {
    const [settings, setSetting] = useState<Partial<ProSettings> | undefined>(defaultSettings);
    const location = useLocation();

    const [collapsed, setCollapsed] = useState(false);

    const convertToAntdMenu = (menus: Menus[]): MenuDataItem[] => {
        return menus && menus.map(item => {
            return {
                name: item.menuName,
                path: item.menuPath,
                icon: item.menuIcon && React.createElement(
                    // @ts-ignore
                    Icon[item.menuIcon],
                    {
                        style:{ fontSize: '16px'}
                    }
                ),
                children: item.children && convertToAntdMenu(item.children),
            }
        });
    }

    return (
        <div id="default-layout">
            <ProLayout
                logo={logo}
                location={{
                    pathname: location.pathname,
                }}
                menu={{
                    request: async () => {
                        const result = await queryMenuTreeList();
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
                            {collapsed ? <MenuUnfoldOutlined /> : <MenuFoldOutlined />}
                        </div>
                    );
                }}
                collapsed={collapsed}
                onCollapse={setCollapsed}
                collapsedButtonRender={false}
                menuHeaderRender={(logo, title) => (
                    <div
                        id="customize_menu_header"
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