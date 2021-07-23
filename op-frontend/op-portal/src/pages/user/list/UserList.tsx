import React, {FC, useEffect, useRef} from "react";
import {useHistory} from "react-router-dom";
import ProTable, {ActionType, ProColumns} from "@ant-design/pro-table";
import {changeUsersEnabled, queryUserPage} from "../../../services/user";
import {User} from "../../../models/User";
import {Button, Dropdown, Menu, Popconfirm, Space} from "antd";
import {ExportOutlined, MinusOutlined, PlusOutlined} from "@ant-design/icons";

type UserListProps = {
    orgId: number;
};

const UserList: FC<UserListProps> = (props) => {
    const {orgId} = props;

    const history = useHistory();
    const ref = useRef<ActionType>();

    const onDeleteUsers = (ids: number[]) => {
        // @ts-ignore
        deleteUsers(ids).then(() => ref.current.reloadAndRest());
    };

    const onChangeUsersEnabled = (ids: number[], enable: boolean) => {
        // @ts-ignore
        changeUsersEnabled(ids, enable).then(() => ref.current.reload());
    };

    useEffect(() => {
        // @ts-ignore
        ref.current.reset()
    }, [orgId]);

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
                <a key="edit" onClick={() => history.push(`/admin/user/edit/${record.id}`)}>
                    编辑
                </a>,
                <Popconfirm
                    title="确定要删除吗？"
                    okText="确定"
                    cancelText="取消"
                    onConfirm={() => onDeleteUsers([record.id] as number[])}
                >
                    <a key="delete">
                        删除
                    </a>
                </Popconfirm>,
                <a key="view" onClick={() => history.push(`/admin/user/detail/${record.id}`)}>
                    查看
                </a>,
                <a key="enable" onClick={() => onChangeUsersEnabled([record.id] as number[], record.status === 0)}>
                    {record.status === 1 ? '禁用' : '启用'}
                </a>,
                <a key="assignRoles" onClick={() => history.push(`/admin/user/assign-roles/${record.id}`)}>
                    分配角色
                </a>,
                <a key="assignResources" onClick={() => history.push(`/admin/user/assign-resources/${record.id}`)}>
                    分配资源
                </a>,
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
            toolBarRender={() => [
                <Button key="button" type="primary" icon={<PlusOutlined/>}
                        onClick={() => history.push('/admin/user/edit', {
                            orgId
                        })}>
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
                            onConfirm={() => onDeleteUsers(selectedRowKeys as number[])}
                        >
                            <Button key="batchDelete" icon={<MinusOutlined/>}>
                                批量删除
                            </Button>
                        </Popconfirm>
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
                    </Space>
                );
            }}
        />
    )
};

export default UserList;