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
import UserAssignOrganizationsPage from "../pages/user/assignorganizations";
import UserGroupListPage from "../pages/usergroup/list";
import UserGroupAssignResourcesPage from "../pages/usergroup/assignresource";
import UserGroupAssignRolesPage from "../pages/usergroup/assignroles";
import UserGroupDetailPage from "../pages/usergroup/detail";
import UserGroupEditPage from "../pages/usergroup/edit";
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
import OauthClientListPage from "../pages/oauthclient/list";
import OauthClientEditPage from "../pages/oauthclient/edit";
import OauthClientDetailPage from "../pages/oauthclient/detail";
import WhiteResourceListPage from "../pages/whiteresource/list";
import WhiteResourceEditPage from "../pages/whiteresource/edit";
import WhiteResourceDetailPage from "../pages/whiteresource/detail";
import JobListPage from "../pages/job/list";
import JobEditPage from "../pages/job/edit";
import JobDetailPage from "../pages/job/detail";
import Authority from "../components/Authority";
import UnauthorizedPage from "../pages/403";
import NoFoundPage from "../pages/404";
import RedirectExternal from "../components/RedirectExternal";

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
                    <Redirect to={"/workbench"}/>
                )
            },
            {
                path: "/admin/organization",
                exact: true,
                component: OrganizationListPage,
                render: () => <Authority value="organization_view" redirect><OrganizationListPage/></Authority>,
            },
            {
                path: "/admin/organization/edit/:id?",
                exact: true,
                component: OrganizationEditPage,
                render: () => <Authority value="organization_save" redirect><OrganizationEditPage/></Authority>,
            },
            {
                path: "/admin/organization/detail/:id",
                exact: true,
                component: OrganizationDetailPage,
                render: () => <Authority value="organization_view" redirect><OrganizationDetailPage/></Authority>,
            },
            {
                path: "/admin/organization/assign-roles/:id",
                exact: true,
                component: OrganizationAssignRolesPage,
                render: () => <Authority value="organization_authorization" redirect><OrganizationAssignRolesPage/></Authority>,
            },
            {
                path: "/admin/organization/assign-resources/:id",
                exact: true,
                component: OrganizationAssignResourcesPage,
                render: () => <Authority value="organization_authorization" redirect><OrganizationAssignResourcesPage/></Authority>,
            },
            {
                path: "/admin/user",
                exact: true,
                component: UserListPage,
                render: () => <Authority value="user_view" redirect><UserListPage/></Authority>,
            },
            {
                path: "/admin/user/edit/:id?",
                exact: true,
                component: UserEditPage,
                render: () => <Authority value="user_save" redirect><UserEditPage/></Authority>,
            },
            {
                path: "/admin/user/detail/:id",
                exact: true,
                component: UserDetailPage,
                render: () => <Authority value="user_view" redirect><UserDetailPage/></Authority>,
            },
            {
                path: "/admin/user/assign-roles/:id",
                exact: true,
                component: UserAssignRolesPage,
                render: () => <Authority value="user_authorization" redirect><UserAssignRolesPage/></Authority>,
            },
            {
                path: "/admin/user/assign-resources/:id",
                exact: true,
                component: UserAssignResourcesPage,
                render: () => <Authority value="user_authorization" redirect><UserAssignResourcesPage/></Authority>,
            },
            {
                path: "/admin/user/assign-organizations/:id",
                exact: true,
                component: UserAssignOrganizationsPage,
                render: () => <Authority value="organization_authorization" redirect><UserAssignOrganizationsPage/></Authority>,
            },
            {
                path: "/admin/userGroup",
                exact: true,
                component: UserGroupListPage,
                render: () => <Authority value="user_group_view" redirect><UserGroupListPage/></Authority>,
            },
            {
                path: "/admin/userGroup/edit/:id?",
                exact: true,
                component: UserGroupEditPage,
                render: () => <Authority value="user_group_save" redirect><UserGroupEditPage/></Authority>,
            },
            {
                path: "/admin/userGroup/detail/:id",
                exact: true,
                component: UserGroupDetailPage,
                render: () => <Authority value="user_group_view" redirect><UserGroupDetailPage/></Authority>,
            },
            {
                path: "/admin/userGroup/assign-roles/:id",
                exact: true,
                component: UserGroupAssignRolesPage,
                render: () => <Authority value="user_group_authorization" redirect><UserGroupAssignRolesPage/></Authority>,
            },
            {
                path: "/admin/userGroup/assign-resources/:id",
                exact: true,
                component: UserGroupAssignResourcesPage,
                render: () => <Authority value="user_group_authorization" redirect><UserGroupAssignResourcesPage/></Authority>,
            },
            {
                path: "/admin/role",
                exact: true,
                component: RoleListPage,
                render: () => <Authority value="role_view" redirect><RoleListPage/></Authority>,
            },
            {
                path: "/admin/role/edit/:id?",
                exact: true,
                component: RoleEditPage,
                render: () => <Authority value="role_save" redirect><RoleEditPage/></Authority>,
            },
            {
                path: "/admin/role/detail/:id",
                exact: true,
                component: RoleDetailPage,
                render: () => <Authority value="role_view" redirect><RoleDetailPage/></Authority>,
            },
            {
                path: "/admin/role/assign-resources/:id",
                exact: true,
                component: RoleAssignResourcesPage,
                render: () => <Authority value="role_authorization" redirect><RoleAssignResourcesPage/></Authority>,
            },
            {
                path: "/admin/resourceCategory",
                exact: true,
                component: ResourceCategoryListPage,
                render: () => <Authority value="resource_category_view" redirect><ResourceCategoryListPage/></Authority>,
            },
            {
                path: "/admin/resourceCategory/edit/:id?",
                exact: true,
                component: ResourceCategoryEditPage,
                render: () => <Authority value="resource_category_save" redirect><ResourceCategoryEditPage/></Authority>,
            },
            {
                path: "/admin/resourceCategory/detail/:id",
                exact: true,
                component: ResourceCategoryDetailPage,
                render: () => <Authority value="resource_category_view" redirect><ResourceCategoryDetailPage/></Authority>,
            },
            {
                path: "/admin/resource",
                exact: true,
                component: ResourceListPage,
                render: () => <Authority value="resource_view" redirect><ResourceListPage/></Authority>,
            },
            {
                path: "/admin/resource/edit/:id?",
                exact: true,
                component: ResourceEditPage,
                render: () => <Authority value="resource_save" redirect><ResourceEditPage/></Authority>,
            },
            {
                path: "/admin/resource/detail/:id",
                exact: true,
                component: ResourceDetailPage,
                render: () => <Authority value="resource_view" redirect><ResourceDetailPage/></Authority>,
            },
            {
                path: "/admin/menu",
                exact: true,
                component: MenuListPage,
                render: () => <Authority value="menu_view" redirect><MenuListPage/></Authority>,
            },
            {
                path: "/admin/menu/edit/:id?",
                exact: true,
                component: MenuEditPage,
                render: () => <Authority value="menu_save" redirect><MenuEditPage/></Authority>,
            },
            {
                path: "/admin/menu/detail/:id",
                exact: true,
                component: MenuDetailPage,
                render: () => <Authority value="menu_view" redirect><MenuDetailPage/></Authority>,
            },
            {
                path: "/admin/oauthClient",
                exact: true,
                component: OauthClientListPage,
                render: () => <Authority value="oauth_client_view" redirect><OauthClientListPage/></Authority>,
            },
            {
                path: "/admin/oauthClient/edit/:id?",
                exact: true,
                component: OauthClientEditPage,
                render: () => <Authority value="oauth_client_save" redirect><OauthClientEditPage/></Authority>,
            },
            {
                path: "/admin/oauthClient/detail/:id",
                exact: true,
                component: OauthClientDetailPage,
                render: () => <Authority value="oauth_client_view" redirect><OauthClientDetailPage/></Authority>,
            },
            {
                path: "/admin/whiteResource",
                exact: true,
                component: WhiteResourceListPage,
                render: () => <Authority value="white_resource_view" redirect><WhiteResourceListPage/></Authority>,
            },
            {
                path: "/admin/whiteResource/edit/:id?",
                exact: true,
                component: WhiteResourceEditPage,
                render: () => <Authority value="white_resource_save" redirect><WhiteResourceEditPage/></Authority>,
            },
            {
                path: "/admin/whiteResource/detail/:id",
                exact: true,
                component: WhiteResourceDetailPage,
                render: () => <Authority value="white_resource_view" redirect><WhiteResourceDetailPage/></Authority>,
            },
            {
                path: "/job/quartzJob",
                exact: true,
                component: JobListPage,
            },
            {
                path: "/job/quartzJob/edit/:id?",
                exact: true,
                component: JobEditPage,
            },
            {
                path: "/job/quartzJob/detail/:id",
                exact: true,
                component: JobDetailPage,
            },
            {
                path: "/job/xxlJob",
                exact: true,
                render: () => (
                    <RedirectExternal url = "http://localhost:8090/xxl-job-admin/"/>
                ),
            },
            {
                path: "/403",
                exact: true,
                component: UnauthorizedPage,
            },
            {
                component: NoFoundPage,
            },
        ],
    },
];

export default routes;
