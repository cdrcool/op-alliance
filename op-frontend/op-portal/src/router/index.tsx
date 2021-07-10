import React from 'react';
import {Redirect} from 'react-router-dom';
import DefaultLayout from '../layouts/DefaultLayout';
import UserListPage from "../pages/user/list";
import NoFoundPage from "../pages/404";
import MenuListPage from "../pages/menu/list";
import ResourceCategoryPage from "../pages/resourcecategory/list";
import ResourceListPage from "../pages/resource/list";
import RoleListPage from "../pages/role/list";
import RoleEditPage from "../pages/role/edit";
import RoleDetailPage from "../pages/role/detail";
import UserEditPage from "../pages/user/edit";
import UserDetailPage from "../pages/user/detail";
import BasicLayout from "../layouts/BasicLayout";
import MenuEditPage from "../pages/menu/edit";
import MenuDetailPage from "../pages/menu/detail";

const routes  = [
    {
        path: "/",
        component: DefaultLayout,
        routes: [
            {
                path: "/",
                exact: true,
                render: () => (
                    <Redirect to={"/workbench"}/>
                )
            },
            {
                path: "/admin/user",
                exact: true,
                component: UserListPage,
            },
            {
                path: "/admin/user/edit/:id?",
                exact: true,
                component: UserEditPage,
            },
            {
                path: "/admin/user/detail/:id",
                exact: true,
                component: UserDetailPage,
            },
            {
                path: "/admin/role",
                exact: true,
                component: RoleListPage,
            },
            {
                path: "/admin/role/edit/:id?",
                exact: true,
                component: RoleEditPage,
            },
            {
                path: "/admin/role/detail/:id",
                exact: true,
                component: RoleDetailPage,
            },
            {
                path: "/admin/menu",
                exact: true,
                component: MenuListPage,
            },
            {
                path: "/admin/menu/edit/:id?",
                exact: true,
                component: MenuEditPage,
            },
            {
                path: "/admin/menu/detail/:id",
                exact: true,
                component: MenuDetailPage,
            },
            {
                path: "/admin/resourceCategory",
                exact: true,
                component: ResourceCategoryPage,
            },
            {
                path: "/admin/resource",
                exact: true,
                component: ResourceListPage,
            },
            {
                component: NoFoundPage,
            },
        ],
    },
];

export default routes;