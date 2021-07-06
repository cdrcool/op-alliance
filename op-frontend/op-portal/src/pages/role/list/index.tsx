import React, {FC} from "react";
import {Button, Card, Dropdown, Menu, Popconfirm, Space} from "antd";
import {ExportOutlined, MinusOutlined, PlusOutlined} from "@ant-design/icons";

import type {ProColumns} from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import {Role} from "../../../models/Role";
import {queryRolePage} from "../../../services/role";

const columns: ProColumns<Role>[] = [
    {
        title: '角色名',
        dataIndex: 'roleName',
    },
    {
        title: '角色编码',
        dataIndex: 'roleCode',
    },
    {
        title: '启用状态',
        dataIndex: 'status',
        valueEnum: {
            0: {text: '禁用', status: 'Error'},
            1: {text: '启用', status: 'Success'},
        },
        filters: true,
        onFilter: true,
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
            <a key="enable">
                启用
            </a>,
            <a key="view">
                查看
            </a>,
        ],
    },
];

const RoleListPage: FC = () => {
    return (
        <Card size="small" className="card">
            <ProTable<Role>
                rowKey="id"
                search={false}
                headerTitle="角色列表"
                options={{
                    search: {
                        placeholder: "输入角色名、角色编码或角色描述查询",
                        style: {width: 400},
                    },
                    fullScreen: true,
                }}
                columns={columns}
                request={async ({current, pageSize}, sort, filter) => {
                    console.log('sort: ', JSON.stringify(sort));
                    console.log('filter: ', JSON.stringify(filter));
                    const result = await queryRolePage(
                        current || 0,
                        pageSize || 10,
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
                            <Dropdown.Button overlay={
                                <Menu onClick={(menuInfo) => {
                                }}>
                                    <Menu.Item key={1}>批量启用</Menu.Item>
                                    <Menu.Item key={0}>批量禁用</Menu.Item>
                                </Menu>
                            }
                            >
                                启用 | 禁用
                            </Dropdown.Button>
                        </Space>
                    );
                }}
            />
        </Card>
    )
};

export default RoleListPage;
