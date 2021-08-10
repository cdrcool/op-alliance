import React, {FC, useEffect, useRef} from "react";
import {useHistory} from "react-router-dom";
import {Button, Dropdown, Menu, Popconfirm, Space} from "antd";
import {ExportOutlined, MinusOutlined, PlusOutlined} from "@ant-design/icons";
import ProTable, {ActionType, ProColumns} from "@ant-design/pro-table";
import {User} from "../../../models/User";
import {changeUsersEnabled, queryUserPage} from "../../../services/user";
import Authority from "../../../components/Authority";
import AuthTableDropDown from "../../../components/AuthTableDropdown";

type UserListProps = {
    orgId: number;
};

const UserList: FC<UserListProps> = (props) => {
        const {orgId} = props;

        const history = useHistory();
        const ref = useRef<ActionType>();

        useEffect(() => {
            // @ts-ignore
            ref.current.reload();
        }, [orgId]);

        const onDeleteUsers = (ids: number[]) => {
            // @ts-ignore
            deleteUsers(ids).then(() => ref.current.reloadAndRest());
        };

        const onChangeUsersEnabled = (ids: number[], enable: boolean) => {
            // @ts-ignore
            changeUsersEnabled(ids, enable).then(() => ref.current.reload());
        };

        const userColumns: ProColumns<User>[] = [
            {
                title: '用户名',
                dataIndex: 'username',
            },
            {
                title: '昵称',
                dataIndex: 'nickname',
            },
            {
                title: '手机号',
                dataIndex: 'phone',
            },
            {
                title: '邮箱',
                dataIndex: 'email',
            },
            {
                title: '帐号状态',
                dataIndex: 'status',
                valueEnum: {
                    0: {text: '禁用', status: 'Error'},
                    1: {text: '启用', status: 'Success'},
                    2: {text: '过期', status: 'Error'},
                    3: {text: '锁定', status: 'Error'},
                    4: {text: '密码过期', status: 'Error'},
                },
                filters: true,
                onFilter: true,
            },
            {
                title: '操作',
                valueType: 'option',
                render: (text, record, _, action) => [
                    <Authority value="user_save">
                        <a key="edit" onClick={() => history.push(`/admin/user/edit/${record.id}`)}>
                            编辑
                        </a>
                    </Authority>,
                    <Authority value="user_delete">
                        <Popconfirm
                            title="确定要删除吗？"
                            okText="确定"
                            cancelText="取消"
                            onConfirm={() => onDeleteUsers([record.id] as number[])}
                        >
                            <a key="delete">
                                删除
                            </a>
                        </Popconfirm>
                    </Authority>,
                    <Authority value="user_view">
                        <a key="view" onClick={() => history.push(`/admin/user/detail/${record.id}`)}>
                            查看
                        </a>
                    </Authority>,
                    <AuthTableDropDown
                        key="actions"
                        menus={[
                            {
                                // @ts-ignore
                                authority: 'user_change_enabled',
                                key: 'enable',
                                name: record.status === 1 ? '禁用' : '启用',
                                onClick: () => onChangeUsersEnabled([record.id] as number[], record.status === 0),
                            },
                            {
                                // @ts-ignore
                                authority: 'user_authorization',
                                key: 'assignRoles',
                                name: '分配角色',
                                onClick: () => history.push(`/admin/user/assign-roles/${record.id}`),
                            },
                            {
                                // @ts-ignore
                                authority: 'user_authorization',
                                key: 'assignResources',
                                name: '分配资源',
                                onClick: () => history.push(`/admin/user/assign-resources/${record.id}`),
                            },
                            {
                                // @ts-ignore
                                authority: 'user_authorization',
                                key: 'assignOrganizations',
                                name: '授权组织',
                                onClick: () => history.push(`/admin/user/assign-organizations/${record.id}`),
                            },
                        ]}
                    />,
                ],
            },
        ];

        return (
            <ProTable<User>
                actionRef={ref}
                rowKey="id"
                search={false}
                options={{
                    search: {
                        placeholder: "输入用户名、昵称、手机号或邮箱查询",
                        style: {width: 400},
                    },
                    fullScreen: true,
                }}
                toolBarRender={() => [
                    <Authority value="user_save">
                        <Button key="button" type="primary" icon={<PlusOutlined/>}
                                onClick={() => history.push('/admin/user/edit', {
                                    orgId
                                })}>
                            新建
                        </Button>
                    </Authority>,
                    <Authority value="user_export">
                        <Button key="button" icon={<ExportOutlined/>}>
                            导出
                        </Button>
                    </Authority>,
                ]}
                tableAlertOptionRender={({selectedRowKeys}) => {
                    return (
                        <Space>
                            <Authority value="user_delete">
                                <Popconfirm
                                    title="确定要删除吗？"
                                    okText="确定"
                                    cancelText="取消"
                                    onConfirm={() => onDeleteUsers(selectedRowKeys as number[])}
                                >
                                    <Button key="batchDelete" icon={<MinusOutlined/>}>
                                        批量删除
                                    </Button>
                                </Popconfirm>
                            </Authority>
                            <Authority value="user_change_enabled">
                                <Dropdown.Button overlay={
                                    <Menu
                                        onClick={(menuInfo) => onChangeUsersEnabled(selectedRowKeys as number[], parseInt(menuInfo.key) === 1)}>
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
                columns={userColumns}
                request={
                    async (params, sort, filter) => {
                        const {current, pageSize, keyword} = params;
                        const result = await queryUserPage(
                            (current || 1) - 1,
                            pageSize || 10,
                            {
                                orgId,
                                keyword,
                                ...filter
                            }
                        );
                        return {
                            data: result.content,
                            success: true,
                            total: result.totalElements,
                        };
                    }
                }
                pagination={{
                    pageSize: 10,
                }}
                rowSelection={{}}
            />
        )
    }
;

export default UserList;
