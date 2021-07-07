import React, {FC} from "react";
import {Button, Card, Dropdown, Menu, Popconfirm, Space} from "antd";
import {ExportOutlined, MinusOutlined, PlusOutlined} from "@ant-design/icons";

import type {ProColumns} from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import {queryRolePage} from "../../../services/role";
import {User} from "../../../models/User";
import {Menus} from "../../../models/Menus";

const columns: ProColumns<User>[] = [
    {
        title: '菜单名称',
        dataIndex: 'menuName',
    },
    {
        title: '菜单编码',
        dataIndex: 'menuCode',
    },

    {
        title: '菜单路由',
        dataIndex: 'menuRoute',
    },
    {
        title: '菜单图标',
        dataIndex: 'menuIcon',
    },
    {
        title: '是否目录',
        dataIndex: 'isDirectory',
        valueEnum: {
            1: {text: '是'},
            0: {text: '否'},
        },
    },
    {
        title: '是否隐藏',
        dataIndex: 'isHidden',
        valueEnum: {
            1: {text: '是'},
            0: {text: '否'},
        },
    },
    {
        title: '权限标识',
        dataIndex: 'orgName',
        valueType: 'select',
    },
    {
        title: '操作',
        valueType: 'option',
        render: (text, record, _, action) => [
            <a key="edit" onClick={() => {
            }}>
                编辑
            </a>,
            <Popconfirm
                title="确定要删除吗？"
                okText="确定"
                cancelText="取消"
                onConfirm={() => {
                }}
            >
                <a key="delete">
                    删除
                </a>
            </Popconfirm>,
            <a key="view">
                查看
            </a>,
        ],
    },
];

const MenuListPage: FC = () => {
    return (
        <Card size="small" className="card">
            <ProTable<Menus>
                rowKey="id"
                search={false}
                headerTitle="菜单列表"
                options={{
                    search: {
                        placeholder: "输入菜单名称、菜单编码、菜单路由、权限标识查询",
                        style: {width: 400},
                    },
                    fullScreen: true,
                }}
                columns={columns}
                request={
                    async (params, sort, filter) => {
                        const {current, pageSize, keyword} = params;
                        const result = await queryRolePage(
                            (current || 1) - 1,
                            pageSize || 10,
                            {
                                keyword,
                                ...filter
                            }
                        );
                        return {
                            data: result.content,
                            success: true,
                            total: result.totalElements,
                        };
                    }}
                pagination={{
                    pageSize: 10,
                }}
                rowSelection={{}}
                toolBarRender={() => [
                    <Button key="button" icon={<PlusOutlined/>} type="primary">
                        新建
                    </Button>,
                    <Button key="button" icon={<ExportOutlined/>}>
                        导出
                    </Button>,
                ]}
                tableAlertOptionRender={() => {
                    return (
                        <Space>
                            <Popconfirm
                                title="确定要删除吗？"
                                okText="确定"
                                cancelText="取消"
                                onConfirm={() => {
                                }}
                            >
                                <Button key="batchDelete" icon={<MinusOutlined/>}>
                                    批量删除
                                </Button>
                            </Popconfirm>
                        </Space>
                    );
                }}
            />
        </Card>
    )
};

export default MenuListPage;

/**
 * const dataSource = [
 {
            id: 1,
            menuNo: 1,
            menuName: '工作台',
            menuCode: 'workbench',
            menuIcon: null,
            menuRoute: '/workbench',
            isDirectory: 0,
            isHidden: 0,
            permission: 'workbench:view',
        },
 {
            id: 2,
            menuNo: 2,
            menuName: '管理中心',
            menuCode: 'management',
            menuIcon: null,
            menuRoute: '/management',
            isDirectory: 1,
            isHidden: 0,
            children: [
                {
                    id: 3,
                    pid: 2,
                    menuNo: 1,
                    menuName: '组织管理',
                    menuCode: 'organization',
                    menuIcon: null,
                    menuRoute: '/management/organization',
                    isDirectory: 0,
                    isHidden: 0,
                    permission: 'management:organization:view',
                },
                {
                    id: 4,
                    pid: 2,
                    menuNo: 2,
                    menuName: '用户管理',
                    menuCode: 'user',
                    menuIcon: null,
                    menuRoute: '/management/user',
                    isDirectory: 0,
                    isHidden: 0,
                    permission: 'management:user:view',
                },
                {
                    id: 5,
                    pid: 2,
                    menuNo: 3,
                    menuName: '角色',
                    menuCode: 'role',
                    menuIcon: null,
                    menuRoute: '/management/role',
                    isDirectory: 0,
                    isHidden: 0,
                    permission: 'management:role:view',
                },
                {
                    id: 6,
                    pid: 2,
                    menuNo: 4,
                    menuName: '资源管理',
                    menuCode: 'resource',
                    menuIcon: null,
                    menuRoute: '/management/resource',
                    isDirectory: 0,
                    isHidden: 0,
                    permission: 'management:resource:view',
                },
                {
                    id: 7,
                    pid: 2,
                    menuNo: 5,
                    menuName: '菜单管理',
                    menuCode: 'menu',
                    menuIcon: null,
                    menuRoute: '/management/menu',
                    isDirectory: 0,
                    isHidden: 0,
                    permission: 'management:menu:view',
                },
            ],
        },
 {
            id: 3,
            menuNo: 3,
            menuName: '统计中心',
            menuCode: 'statistics',
            menuIcon: null,
            menuRoute: '/statistics',
            isDirectory: 0,
            isHidden: 1,
            permission: 'statistics:view',
        },
 {
            id: 4,
            menuNo: 4,
            menuName: '日志中心',
            menuCode: 'logging',
            menuIcon: null,
            menuRoute: '/logging',
            isDirectory: 0,
            isHidden: 1,
            permission: 'logging:view',
        },
 {
            id: 5,
            menuNo: 5,
            menuName: '监控中心',
            menuCode: 'monitor',
            menuIcon: null,
            menuRoute: '/monitor',
            isDirectory: 0,
            isHidden: 1,
            permission: 'monitor:view',
        },
 {
            id: 6,
            menuNo: 6,
            menuName: '附件中心',
            menuCode: 'attachment',
            menuIcon: null,
            menuRoute: '/attachment',
            isDirectory: 0,
            isHidden: 1,
            permission: 'attachment:view',
        },
 {
            id: 7,
            menuNo: 7,
            menuName: '文档中心',
            menuCode: 'document',
            menuIcon: null,
            menuRoute: '/document',
            isDirectory: 0,
            isHidden: 1,
            permission: 'document:view',
        },
 ]
 */