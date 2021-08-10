import React, {FC, useRef} from "react";
import {useHistory} from "react-router-dom";
import {Button, Popconfirm, Space} from "antd";
import {ExportOutlined, MinusOutlined, PlusOutlined} from "@ant-design/icons";
import {PageContainer} from "@ant-design/pro-layout";
import type {ActionType, ProColumns} from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import {Resource} from "../../../models/Resource";
import {UserGroup} from "../../../models/UserGroup";
import {deleteUserGroups, queryUserGroupPage} from "../../../services/userGroup";
import Authority from "../../../components/Authority";
import AuthTableDropDown from "../../../components/AuthTableDropdown";

const UserGroupListPage: FC = () => {
    const history = useHistory();
    const ref = useRef<ActionType>();

    const onDeleteUserGroups = (ids: number[]) => {
        // @ts-ignore
        deleteUserGroups(ids).then(() => ref.current.reloadAndRest());
    };

    const columns: ProColumns<UserGroup>[] = [
        {
            title: '用户组名称',
            dataIndex: 'groupName',
            search: false,
        },
        {
            title: '用户组描述',
            dataIndex: 'groupDesc',
            search: false,
        },
        {
            title: '操作',
            valueType: 'option',
            render: (text, record, _, action) => [
                <Authority value="user_group_save">
                    <a key="edit" onClick={() => history.push(`/admin/userGroup/edit/${record.id}`)}>
                        编辑
                    </a>
                </Authority>,
                <Authority value="user_group_delete">
                    <Popconfirm
                        title="确定要删除吗？"
                        okText="确定"
                        cancelText="取消"
                        onConfirm={() => onDeleteUserGroups([record.id] as number[])}
                    >
                        <a key="delete">
                            删除
                        </a>
                    </Popconfirm>
                </Authority>,
                <Authority value="user_group_view">
                    <a key="view" onClick={() => history.push(`/admin/userGroup/detail/${record.id}`)}>
                        查看
                    </a>
                </Authority>,
                <AuthTableDropDown
                    key="actions"
                    menus={[
                        {
                            // @ts-ignore
                            authority: 'user_group_authorization',
                            key: 'assignRoles',
                            name: '分配角色',
                            onClick: () => history.push(`/admin/userGroup/assign-roles/${record.id}`),
                        },
                        {
                            // @ts-ignore
                            authority: 'user_group_authorization',
                            key: 'assignResources',
                            name: '分配资源',
                            onClick: () => history.push(`/admin/userGroup/assign-resources/${record.id}`),
                        },
                    ]}
                />,
            ],
        },
    ];

    return (
        <PageContainer
            className="page-container"
            title="资源管理"
            header={{
                breadcrumb: {},
            }}>
            <ProTable<Resource>
                actionRef={ref}
                rowKey="id"
                search={false}
                options={{
                    search: {
                        placeholder: "输入用户组名称或用户组描述查询",
                        style: {width: 400},
                    },
                    fullScreen: true,
                }}
                toolBarRender={() => [
                    <Authority value="user_group_save">
                        <Button key="button" type="primary" icon={<PlusOutlined/>}
                                onClick={() => history.push('/admin/userGroup/edit')}>
                            新建
                        </Button>
                    </Authority>,
                    <Authority value="user_group_export">
                        <Button key="button" icon={<ExportOutlined/>}>
                            导出
                        </Button>
                    </Authority>,
                ]}
                tableAlertOptionRender={({selectedRowKeys}) => {
                    return (
                        <Space>
                            <Authority value="user_group_delete">
                                <Popconfirm
                                    title="确定要删除吗？"
                                    okText="确定"
                                    cancelText="取消"
                                    onConfirm={() => onDeleteUserGroups(selectedRowKeys as number[])}
                                >
                                    <Button key="batchDelete" icon={<MinusOutlined/>}>
                                        批量删除
                                    </Button>
                                </Popconfirm>
                            </Authority>
                        </Space>
                    );
                }}
                columns={columns}
                request={
                    async (params, sort, filter) => {
                        const {current, pageSize, ...others} = params;
                        const result = await queryUserGroupPage(
                            (current || 1) - 1,
                            pageSize || 10,
                            {
                                ...others,
                                ...filter,
                            }
                        );
                        return {
                            data: result.content,
                            success: true,
                            total: result.totalElements,
                        };
                    }
                }
                rowSelection={{}}
                pagination={{
                    pageSize: 10,
                }}
            />
        </PageContainer>
    )
};

export default UserGroupListPage;
