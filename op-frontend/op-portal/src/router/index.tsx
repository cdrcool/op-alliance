import React from 'react';
import {Redirect} from 'react-router-dom';
import BasicLayout from '../layouts/BasicLayout';
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

const routes  = [
    {
        path: "/",
        component: BasicLayout,
        routes: [
            {
                path: "/",
                exact: true,
                render: () => (
                    <Redirect to={"/management/role-detail/1"}/>
                )
            },
            {
                path: "/management/user",
                exact: true,
                component: UserListPage,
            },
            {
                path: "/management/user-edit/:id?",
                exact: true,
                component: UserEditPage,
            },
            {
                path: "/management/user-detail/:id",
                exact: true,
                component: UserDetailPage,
            },
            {
                path: "/management/role",
                exact: true,
                component: RoleListPage,
            },
            {
                path: "/management/role-edit/:id?",
                exact: true,
                component: RoleEditPage,
            },
            {
                path: "/management/role-detail/:id",
                exact: true,
                component: RoleDetailPage,
            },
            {
                path: "/management/menu",
                exact: true,
                component: MenuListPage,
            },
            {
                path: "/management/resourceCategory",
                exact: true,
                component: ResourceCategoryPage,
            },
            {
                path: "/management/resource",
                exact: true,
                component: ResourceListPage,
            },
            {
                component: NoFoundPage,
            },
        ],
    }
];

export default routes;