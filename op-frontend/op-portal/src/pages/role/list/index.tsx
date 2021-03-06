import React, {FC, useRef} from "react";
import {useHistory} from "react-router-dom";
import {Button, Dropdown, Menu, Popconfirm, Space} from "antd";
import {ExportOutlined, MinusOutlined, PlusOutlined} from "@ant-design/icons";
import {PageContainer} from "@ant-design/pro-layout";
import type {ActionType, ProColumns} from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import {Role} from "../../../models/Role";
import {changeRolesEnabled, deleteRoles, queryRolePage} from "../../../services/role";
import Authority from "../../../components/Authority";
import AuthTableDropDown from "../../../components/AuthTableDropdown";

const RoleListPage: FC = () => {
    const history = useHistory();
    const ref = useRef<ActionType>();

    const onDeleteRoles = (ids: number[]) => {
        // @ts-ignore
        deleteRoles(ids).then(() => ref.current.reloadAndRest());
    };

    const onChangeRolesEnabled = (ids: number[], enable: boolean) => {
        // @ts-ignore
        changeRolesEnabled(ids, enable).then(() => ref.current.reload());
    };

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
            title: '角色描述',
            dataIndex: 'roleDesc',
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
                <Authority value="role_save">
                    <a key="edit" onClick={() => history.push(`/admin/role/edit/${record.id}`)}>
                        编辑
                    </a>
                </Authority>,
                <Authority value="role_delete">
                    <Popconfirm
                        title="确定要删除吗？"
                        okText="确定"
                        cancelText="取消"
                        onConfirm={() => onDeleteRoles([record.id] as number[])}
                    >
                        <a key="delete">
                            删除
                        </a>
                    </Popconfirm>
                </Authority>,
                <Authority value="role_view">
                    <a key="view" onClick={() => history.push(`/admin/role/detail/${record.id}`)}>
                        查看
                    </a>
                </Authority>,
                <AuthTableDropDown
                    key="actions"
                    menus={[
                        {
                            // @ts-ignore
                            authority: 'role_change_enabled',
                            key: 'enable',
                            name: record.status === 1 ? '禁用' : '启用',
                            onClick: () => onChangeRolesEnabled([record.id] as number[], record.status === 0),
                        },
                        {
                            // @ts-ignore
                            authority: 'role_authorization',
                            key: 'assignResources',
                            name: '分配资源',
                            onClick: () => history.push(`/admin/role/assign-resources/${record.id}`),
                        },
                    ]}
                />,
            ],
        },
    ];

    return (
        <PageContainer className="page-container" header={{
            breadcrumb: {},
        }}>
            <ProTable<Role>
                actionRef={ref}
                rowKey="id"
                search={false}
                options={{
                    search: {
                        placeholder: "输入角色名、角色编码或角色描述查询",
                        style: {width: 400},
                    },
                    fullScreen: true,
                }}
                toolBarRender={() => [
                    <Authority value="role_save">
                        <Button key="button" type="primary" icon={<PlusOutlined/>}
                                onClick={() => history.push('/admin/role/edit')}>
                            新建
                        </Button>
                    </Authority>,
                    <Authority value="role_export">
                        <Button key="button" icon={<ExportOutlined/>}>
                            导出
                        </Button>
                    </Authority>,
                ]}
                tableAlertOptionRender={({selectedRowKeys}) => {
                    return (
                        <Space>
                            <Authority value="role_delete">
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
                            </Authority>
                            <Authority value="role_change_enabled">
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
                            </Authority>
                        </Space>
                    );
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
                rowSelection={{}}
                pagination={{
                    pageSize: 10,
                }}
            />
        </PageContainer>
    )
};

export default RoleListPage;
