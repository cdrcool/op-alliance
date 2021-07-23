import React from 'react';
import {Redirect} from 'react-router-dom';
import DefaultLayout from '../layouts/DefaultLayout';
import UserListPage from "../pages/user/list";
import NoFoundPage from "../pages/404";
import MenuListPage from "../pages/menu/list";
import ResourceCategoryPage from "../pages/resourcecategory/list";
import RoleListPage from "../pages/role/list";
import RoleEditPage from "../pages/role/edit";
import RoleDetailPage from "../pages/role/detail";
import UserEditPage from "../pages/user/edit";
import UserDetailPage from "../pages/user/detail";
import MenuEditPage from "../pages/menu/edit";
import MenuDetailPage from "../pages/menu/detail";
import OrganizationListPage from "../pages/organization/list";
import OrganizationEditPage from "../pages/organization/edit";
import OrganizationDetailPage from "../pages/organization/detail";
import ResourceEditPage from "../pages/resource/edit";
import ResourceDetailPage from "../pages/resource/detail";
import ResourceListPage from "../pages/resource/list";
import ResourceCategoryEditPage from "../pages/resourcecategory/edit";
import ResourceCategoryDetailPage from "../pages/resourcecategory/detail";
import RoleAssignResourcesPage from "../pages/role/assignresource";
import UserAssignResourcesPage from "../pages/user/assignresource";
import OrganizationAssignResourcesPage from "../pages/organization/assignresource";
import UserAssignRolesPage from "../pages/user/assignroles";
import OrganizationAssignRolesPage from "../pages/organization/assignroles";
import LoginPage from "../pages/login";
import SecurityLayout from "../layouts/SecurityLayout";

const routes = [
    {
        path: "/",
        component: SecurityLayout,
        routes: [
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
                            <Redirect to={"/workbench"}/>
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
                        component: NoFoundPage,
                    },
                ],
            },
        ],
    },
];

export default routes;