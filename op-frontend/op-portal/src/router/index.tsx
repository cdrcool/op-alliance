import React from 'react';
import {Redirect} from 'react-router-dom';
import LoginPage from "../pages/login";
import DefaultLayout from '../layouts/DefaultLayout';
import OrganizationListPage from "../pages/organization/list";
import OrganizationEditPage from "../pages/organization/edit";
import OrganizationDetailPage from "../pages/organization/detail";
import OrganizationAssignRolesPage from "../pages/organization/assignroles";
import OrganizationAssignResourcesPage from "../pages/organization/assignresource";
import UserListPage from "../pages/user/list";
import UserEditPage from "../pages/user/edit";
import UserDetailPage from "../pages/user/detail";
import UserAssignRolesPage from "../pages/user/assignroles";
import UserAssignResourcesPage from "../pages/user/assignresource";
import RoleListPage from "../pages/role/list";
import RoleEditPage from "../pages/role/edit";
import RoleDetailPage from "../pages/role/detail";
import RoleAssignResourcesPage from "../pages/role/assignresource";
import ResourceCategoryListPage from "../pages/resourcecategory/list";
import ResourceCategoryEditPage from "../pages/resourcecategory/edit";
import ResourceCategoryDetailPage from "../pages/resourcecategory/detail";
import ResourceListPage from "../pages/resource/list";
import ResourceEditPage from "../pages/resource/edit";
import ResourceDetailPage from "../pages/resource/detail";
import MenuListPage from "../pages/menu/list";
import MenuEditPage from "../pages/menu/edit";
import MenuDetailPage from "../pages/menu/detail";
import NoFoundPage from "../pages/404";

const routes = [
    {
        path: "/login",
        exact: true,
        component: LoginPage,
    },
    {
        path: "/",
        component: DefaultLayout,
        routes: [
            {
                path: "/",
                exact: true,
                render: () => (
                    <Redirect to={"/admin/organization"}/>
                )
            },
            {
                path: "/admin/organization",
                exact: true,
                component: OrganizationListPage,
            },
            {
                path: "/admin/organization/edit/:id?",
                exact: true,
                component: OrganizationEditPage,
            },
            {
                path: "/admin/organization/detail/:id",
                exact: true,
                component: OrganizationDetailPage,
            },
            {
                path: "/admin/organization/assign-roles/:id",
                exact: true,
                component: OrganizationAssignRolesPage,
            },
            {
                path: "/admin/organization/assign-resources/:id",
                exact: true,
                component: OrganizationAssignResourcesPage,
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
                path: "/admin/user/assign-roles/:id",
                exact: true,
                component: UserAssignRolesPage,
            },
            {
                path: "/admin/user/assign-resources/:id",
                exact: true,
                component: UserAssignResourcesPage,
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
                path: "/admin/role/assign-resources/:id",
                exact: true,
                component: RoleAssignResourcesPage,
            },
            {
                path: "/admin/resourceCategory",
                exact: true,
                component: ResourceCategoryListPage,
            },
            {
                path: "/admin/resourceCategory/edit/:id?",
                exact: true,
                component: ResourceCategoryEditPage,
            },
            {
                path: "/admin/resourceCategory/detail/:id",
                exact: true,
                component: ResourceCategoryDetailPage,
            },
            {
                path: "/admin/resource",
                exact: true,
                component: ResourceListPage,
            },
            {
                path: "/admin/resource/edit/:id?",
                exact: true,
                component: ResourceEditPage,
            },
            {
                path: "/admin/resource/detail/:id",
                exact: true,
                component: ResourceDetailPage,
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
                component: NoFoundPage,
            },
        ],
    },
];

export default routes;