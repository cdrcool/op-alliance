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
                <a key="edit" onClick={() => history.push(`/admin/userGroup/edit/${record.id}`)}>
                    编辑
                </a>,
                <Popconfirm
                    title="确定要删除吗？"
                    okText="确定"
                    cancelText="取消"
                    onConfirm={() => onDeleteUserGroups([record.id] as number[])}
                >
                    <a key="delete">
                        删除
                    </a>
                </Popconfirm>,
                <a key="view" onClick={() => history.push(`/admin/userGroup/detail/${record.id}`)}>
                    查看
                </a>,
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
                    <Button key="button" type="primary" icon={<PlusOutlined/>}
                            onClick={() => history.push('/admin/userGroup/edit')}>
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
                                onConfirm={() => onDeleteUserGroups(selectedRowKeys as number[])}
                            >
                                <Button key="batchDelete" icon={<MinusOutlined/>}>
                                    批量删除
                                </Button>
                            </Popconfirm>
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
