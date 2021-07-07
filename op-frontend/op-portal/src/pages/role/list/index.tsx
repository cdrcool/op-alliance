import React, {FC, useRef} from "react";
import {Button, Card, Dropdown, Menu, Popconfirm, Space} from "antd";
import {ExportOutlined, MinusOutlined, PlusOutlined} from "@ant-design/icons";

import type {ActionType, ProColumns} from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import {Role} from "../../../models/Role";
import {changeRolesEnabled, deleteRoles, queryRolePage} from "../../../services/role";
import {useHistory} from "react-router-dom";

const RoleListPage: FC = () => {
    const history = useHistory();
    const ref = useRef<ActionType>();

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
                <a key="edit" onClick={() => history.push(`/management/role-edit/${record.id}`)}>
                    编辑
                </a>,
                <Popconfirm
                    title="确定要删除吗？"
                    okText="确定"
                    cancelText="取消"
                    onConfirm={() => onDeleteRoles([record.id] as number[])}
                >
                    <a key="delete">
                        删除
                    </a>
                </Popconfirm>,
                <a key="view" onClick={() => history.push(`/management/role-detail/${record.id}`)}>
                    查看
                </a>,
                <a key="enable" onClick={() => onChangeRolesEnabled([record.id] as number[], record.status === 0)}>
                    {record.status === 1 ? '禁用' : '启用'}
                </a>,
            ],
        },
    ];

    const onDeleteRoles = (ids: number[]) => {
        // @ts-ignore
        deleteRoles(ids).then(() => ref.current.reloadAndRest());
    }

    const onChangeRolesEnabled = (ids: number[], enable: boolean) => {
        // @ts-ignore
        changeRolesEnabled(ids, enable).then(() => ref.current.reload());
    }

    return (
        <Card size="small" className="card">
            <ProTable<Role>
                actionRef={ref}
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
                    <Button key="button" type="primary" icon={<PlusOutlined/>}
                            onClick={() => history.push('/management/role-edit')}>
                        新建
                    </Button>,
                    <Button key="button" icon={<ExportOutlined/>}>
                        导出
                    </Button>,
                ]}
                tableAlertOptionRender={({selectedRowKeys}) => {
                    return (
                        <Space>
                            <Popconfirm
                                title="确定要删除吗？"
                                okText="确定"
                                cancelText="取消"
                                onConfirm={() => onDeleteRoles(selectedRowKeys as number[])}
                            >
                                <Button key="batchDelete" icon={<MinusOutlined/>}>
                                    批量删除
                                </Button>
                            </Popconfirm>
                            <Dropdown.Button overlay={
                                <Menu
                                    onClick={(menuInfo) => onChangeRolesEnabled(selectedRowKeys as number[], parseInt(menuInfo.key) === 1)}>
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
