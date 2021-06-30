import React from 'react';
import {Redirect} from 'react-router-dom';
import BasicLayout from '../layouts/BasicLayout';
import UserPage from "../pages/User/list";
import NoFoundPage from "../pages/404";
import MenuPage from "../pages/Menu/list";

const routes  = [
    {
        path: "/",
        component: BasicLayout,
        routes: [
            {
                path: "/",
                exact: true,
                render: () => (
                    <Redirect to={"/management/user"}/>
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
                component: NoFoundPage,
            },
        ],
    }
];

export default routes;