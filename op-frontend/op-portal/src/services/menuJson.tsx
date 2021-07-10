import {DesktopOutlined, TeamOutlined} from "@ant-design/icons";

export default [
    {
        path: '/workbench',
        name: '工作台',
        icon: <DesktopOutlined/>,
    },
    {
        path: '/admin',
        name: '管理中心',
        icon: <TeamOutlined/>,
        children: [
            {
                path: '/admin/organization',
                name: '组织管理',
            },
            {
                path: '/admin/user',
                name: '用户管理',
            },
            {
                path: '/admin/role',
                name: '角色管理',
            },
            {
                path: '/admin/resourceCategory',
                name: '资源管理',
            },
            {
                path: '/admin/menu',
                name: '菜单管理',
            },
        ]
    },
]