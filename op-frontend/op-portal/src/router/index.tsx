import React from 'react';
import {Redirect} from 'react-router-dom';
import BasicLayout from '../layouts/BasicLayout';
import UserPage from "../pages/user/list";
import NoFoundPage from "../pages/404";
import MenuPage from "../pages/menu/list";
import ResourcePage from "../pages/resource/list";

const routes  = [
    {
        path: "/",
        component: BasicLayout,
        routes: [
            {
                path: "/",
                exact: true,
                render: () => (
                    <Redirect to={"/management/resource"}/>
                )
            },
            {
                path: "/management/user",
                exact: true,
                component: UserPage,
            },
            {
                path: "/management/menu",
                exact: true,
                component: MenuPage,
            },
            {
                path: "/management/resource",
                exact: true,
                component: ResourcePage,
            },
            {
                component: NoFoundPage,
            },
        ],
    }
];

export default routes;