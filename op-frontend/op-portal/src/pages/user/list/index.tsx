import React, {FC, useEffect, useRef, useState} from 'react';
import ProCard from '@ant-design/pro-card';
import {Button, Card, Dropdown, Menu, Popconfirm, Space} from "antd";
import {ExportOutlined, MinusOutlined, PlusOutlined} from "@ant-design/icons";
import ProTable, {ActionType, ProColumns} from "@ant-design/pro-table";
import {useHistory} from "react-router-dom";
import {User} from "../../../models/User";
import {queryOrganizationTree} from "../../../services/organization";
import {Organization} from "../../../models/Organization";
import {changeUsersEnabled, queryUserPage} from "../../../services/user";

import './index.css';

type OrganizationTreeProps = {
    orgId: number;
    onChange: (id: number) => void;
};

const OrganizationTree: React.FC<OrganizationTreeProps> = (props) => {
    const {orgId, onChange} = props;

    const orgColumns: ProColumns<Organization>[] = [
        {
            title: '组织名称',
            dataIndex: 'orgName',
        },
    ];

    const [rowKey, setRowKey] = useState<number>(1);
    return (
        <ProTable<Organization>
            rowKey="id"
            search={false}
            options={{
                search: {
                    placeholder: "输入组织名称查询",
                    style: {width: 200},
                },
                fullScreen: false,
                reload: false,
                setting: false,
                density: false,
            }}
            expandable={
                {
                    defaultExpandedRowKeys: [orgId],
                }
            }
            scroll={{ y: document.body.offsetHeight - 200 }}
            columns={orgColumns}
            pagination={false}
            onRow={
                (record, rowIndex) => {
                    return {
                        onClick: event => {
                            setRowKey(record.id as number);
                            onChange(record.id as number);
                        }
                    };
                }}
            rowClassName={(record, index) => {return record.id === rowKey ? "rowSelected" : "rowUnSelected"}}
            request={
                async (params) => {
                    const {keyword} = params;
                    const result = await queryOrganizationTree(
                        {
                            keyword,
                        }
                    );
                    return {
                        data: [result],
                        success: true,
                    };
                }}
        />
    )
};

type UserListProps = {
    orgId: number;
};

const UserList: React.FC<UserListProps> = (props) => {
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
            title: '性别',
            dataIndex: 'gender',
            valueEnum: {
                1: {text: '男'},
                2: {text: '女'},
            },
            filters: true,
            onFilter: true,
        },
        {
            title: '出生日期',
            dataIndex: 'birthday',
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
                <a key="edit" onClick={() => history.push(`/management/user-edit/${record.id}`)}>
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
                <a key="view" onClick={() => history.push(`/management/user-detail/${record.id}`)}>
                    查看
                </a>,
                <a key="enable" onClick={() => onChangeUsersEnabled([record.id] as number[], record.status === 0)}>
                    {record.status === 1 ? '禁用' : '启用'}
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
                }}
            pagination={{
                pageSize: 10,
            }}
            rowSelection={{}}
            toolBarRender={() => [
                <Button key="button" type="primary" icon={<PlusOutlined/>}
                        onClick={() => history.push('/management/user-edit')}>
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

const UserListPage: FC = () => {
    const [orgId, setOrgId] = useState<number>(1);

    return (
        <Card size="small" className="card" id="user-page">
            <ProCard split="vertical">
                <ProCard title="组织机构" colSpan={6} className="left">
                    <OrganizationTree orgId={orgId} onChange={(id) => setOrgId(id)}/>
                </ProCard>
                <ProCard title="用户列表">
                    <UserList orgId={orgId}/>
                </ProCard>
            </ProCard>
        </Card>
    );
};

export default UserListPage;