import React from 'react';
import {Redirect} from 'react-router-dom';
import BasicLayout from '../layouts/BasicLayout';
import UserPage from "../pages/User/page";

const routes  = [
    {
        path: "/",
        component: BasicLayout,
        routes: [
            {
                path: "/",
                exact: true,
                render: () => (
                    <Redirect to={"/user"}/>
                )
            },
            {
                path: "/user",
                exact: true,
                component: UserPage,
            }
        ],
    }
];

export default routes;