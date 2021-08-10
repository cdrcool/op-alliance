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
import Authority from "../components/Authority";
import UnauthorizedPage from "../pages/403";
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
                    <Redirect to={"/workbench"}/>
                )
            },
            {
                path: "/admin/organization",
                exact: true,
                component: OrganizationListPage,
                render: () => <Authority value="organization_view"><OrganizationListPage/></Authority>,
            },
            {
                path: "/admin/organization/edit/:id?",
                exact: true,
                component: OrganizationEditPage,
                render: () => <Authority value="organization_save"><OrganizationEditPage/></Authority>,
            },
            {
                path: "/admin/organization/detail/:id",
                exact: true,
                component: OrganizationDetailPage,
                render: () => <Authority value="organization_view"><OrganizationDetailPage/></Authority>,
            },
            {
                path: "/admin/organization/assign-roles/:id",
                exact: true,
                component: OrganizationAssignRolesPage,
                render: () => <Authority value="organization_authorization"><OrganizationAssignRolesPage/></Authority>,
            },
            {
                path: "/admin/organization/assign-resources/:id",
                exact: true,
                component: OrganizationAssignResourcesPage,
                render: () => <Authority value="organization_authorization"><OrganizationListPage/></Authority>,
            },
            {
                path: "/admin/user",
                exact: true,
                component: UserListPage,
                render: () => <Authority value="user_view"><OrganizationListPage/></Authority>,
            },
            {
                path: "/admin/user/edit/:id?",
                exact: true,
                component: UserEditPage,
                render: () => <Authority value="user_save"><OrganizationListPage/></Authority>,
            },
            {
                path: "/admin/user/detail/:id",
                exact: true,
                component: UserDetailPage,
                render: () => <Authority value="user_view"><OrganizationListPage/></Authority>,
            },
            {
                path: "/admin/user/assign-roles/:id",
                exact: true,
                component: UserAssignRolesPage,
                render: () => <Authority value="user_authorization"><OrganizationListPage/></Authority>,
            },
            {
                path: "/admin/user/assign-resources/:id",
                exact: true,
                component: UserAssignResourcesPage,
                render: () => <Authority value="user_authorization"><OrganizationListPage/></Authority>,
            },
            {
                path: "/admin/user/assign-organizations/:id",
                exact: true,
                component: UserAssignOrganizationsPage,
                render: () => <Authority value="organization_authorization"><OrganizationListPage/></Authority>,
            },
            {
                path: "/admin/userGroup",
                exact: true,
                component: UserGroupListPage,
                render: () => <Authority value="user_group_view"><OrganizationListPage/></Authority>,
            },
            {
                path: "/admin/userGroup/edit/:id?",
                exact: true,
                component: UserGroupEditPage,
                render: () => <Authority value="user_group_save"><OrganizationListPage/></Authority>,
            },
            {
                path: "/admin/userGroup/detail/:id",
                exact: true,
                component: UserGroupDetailPage,
                render: () => <Authority value="user_group_view"><OrganizationListPage/></Authority>,
            },
            {
                path: "/admin/userGroup/assign-roles/:id",
                exact: true,
                component: UserGroupAssignRolesPage,
                render: () => <Authority value="user_group_authorization"><OrganizationListPage/></Authority>,
            },
            {
                path: "/admin/userGroup/assign-resources/:id",
                exact: true,
                component: UserGroupAssignResourcesPage,
                render: () => <Authority value="user_group_authorization"><OrganizationListPage/></Authority>,
            },
            {
                path: "/admin/role",
                exact: true,
                component: RoleListPage,
                render: () => <Authority value="role_view"><OrganizationListPage/></Authority>,
            },
            {
                path: "/admin/role/edit/:id?",
                exact: true,
                component: RoleEditPage,
                render: () => <Authority value="role_save"><OrganizationListPage/></Authority>,
            },
            {
                path: "/admin/role/detail/:id",
                exact: true,
                component: RoleDetailPage,
                render: () => <Authority value="role_view"><OrganizationListPage/></Authority>,
            },
            {
                path: "/admin/role/assign-resources/:id",
                exact: true,
                component: RoleAssignResourcesPage,
                render: () => <Authority value="role_authorization"><OrganizationListPage/></Authority>,
            },
            {
                path: "/admin/resourceCategory",
                exact: true,
                component: ResourceCategoryListPage,
                render: () => <Authority value="resource_category_view"><OrganizationListPage/></Authority>,
            },
            {
                path: "/admin/resourceCategory/edit/:id?",
                exact: true,
                component: ResourceCategoryEditPage,
                render: () => <Authority value="resource_category_save"><OrganizationListPage/></Authority>,
            },
            {
                path: "/admin/resourceCategory/detail/:id",
                exact: true,
                component: ResourceCategoryDetailPage,
                render: () => <Authority value="resource_category_view"><OrganizationListPage/></Authority>,
            },
            {
                path: "/admin/resource",
                exact: true,
                component: ResourceListPage,
                render: () => <Authority value="resource_view"><OrganizationListPage/></Authority>,
            },
            {
                path: "/admin/resource/edit/:id?",
                exact: true,
                component: ResourceEditPage,
                render: () => <Authority value="resource_save"><OrganizationListPage/></Authority>,
            },
            {
                path: "/admin/resource/detail/:id",
                exact: true,
                component: ResourceDetailPage,
                render: () => <Authority value="resource_view"><OrganizationListPage/></Authority>,
            },
            {
                path: "/admin/menu",
                exact: true,
                component: MenuListPage,
                render: () => <Authority value="menu_view"><OrganizationListPage/></Authority>,
            },
            {
                path: "/admin/menu/edit/:id?",
                exact: true,
                component: MenuEditPage,
                render: () => <Authority value="menu_save"><OrganizationListPage/></Authority>,
            },
            {
                path: "/admin/menu/detail/:id",
                exact: true,
                component: MenuDetailPage,
                render: () => <Authority value="menu_view"><OrganizationListPage/></Authority>,
            },
            {
                path: "/admin/oauthClient",
                exact: true,
                component: OauthClientListPage,
                render: () => <Authority value="oauth_client_view"><OrganizationListPage/></Authority>,
            },
            {
                path: "/admin/oauthClient/edit/:id?",
                exact: true,
                component: OauthClientEditPage,
                render: () => <Authority value="oauth_client_save"><OrganizationListPage/></Authority>,
            },
            {
                path: "/admin/oauthClient/detail/:id",
                exact: true,
                component: OauthClientDetailPage,
                render: () => <Authority value="oauth_client_view"><OrganizationListPage/></Authority>,
            },
            {
                path: "/admin/whiteResource",
                exact: true,
                component: WhiteResourceListPage,
                render: () => <Authority value="white_resource_view"><OrganizationListPage/></Authority>,
            },
            {
                path: "/admin/whiteResource/edit/:id?",
                exact: true,
                component: WhiteResourceEditPage,
                render: () => <Authority value="white_resource_save"><OrganizationListPage/></Authority>,
            },
            {
                path: "/admin/whiteResource/detail/:id",
                exact: true,
                component: WhiteResourceDetailPage,
                render: () => <Authority value="white_resource_view"><OrganizationListPage/></Authority>,
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