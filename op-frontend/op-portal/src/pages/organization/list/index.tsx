import React, {FC, useRef} from "react";
import {useHistory} from "react-router-dom";
import {Button, Popconfirm, Space} from "antd";
import {ExportOutlined, MinusOutlined} from "@ant-design/icons";
import {PageContainer} from "@ant-design/pro-layout";
import type {ActionType, ProColumns} from '@ant-design/pro-table';
import ProTable, {TableDropdown} from '@ant-design/pro-table';
import {Organization} from "../../../models/Organization";
import {deleteOrganizations, queryOrganizationTreeList} from "../../../services/organization";

const OrganizationListPage: FC = () => {
    const history = useHistory();
    const ref = useRef<ActionType>();

    const onDeleteOrganizations = (ids: number[]) => {
        // @ts-ignore
        deleteOrganizations(ids).then(() => ref.current.reloadAndRest());
    };

    const columns: ProColumns<Organization>[] = [
        {
            title: '组织名称',
            dataIndex: 'orgName',
        },
        {
            title: '组织编码',
            dataIndex: 'orgCode',
        },
        {
            title: '组织类型',
            dataIndex: 'orgType',
            valueEnum: {
                1: {text: '集团'},
                2: {text: '公司'},
                3: {text: '分公司'},
                4: {text: '项目部'},
                5: {text: '部门'},
            },
            filters: true,
            onFilter: true,
        },
        {
            title: '操作',
            valueType: 'option',
            render: (text, record, _, action) => [
                <a key="edit" onClick={() => history.push(`/admin/organization/edit/${record.id}`)}>
                    编辑
                </a>,
                <Popconfirm
                    title="确定要删除吗？"
                    okText="确定"
                    cancelText="取消"
                    onConfirm={() => onDeleteOrganizations([record.id] as number[])}
                >
                    <a key="delete">
                        删除
                    </a>
                </Popconfirm>,
                <a key="view" onClick={() => history.push(`/admin/organization/detail/${record.id}`)}>
                    查看
                </a>,
                <a key="addChild" onClick={() => history.push(`/admin/organization/edit`, {
                    pid: record.id,
                })}>
                    新增下级
                </a>,
                <TableDropdown
                    key="actions"
                    menus={[
                        {
                            key: 'assignRoles',
                            name: '分配角色',
                            onClick: () => history.push(`/admin/organization/assign-roles/${record.id}`),
                        },
                        {
                            key: 'assignResources',
                            name: '分配资源',
                            onClick: () => history.push(`/admin/organization/assign-resources/${record.id}`),
                        },
                    ]}
                />,
            ],
        },
    ];

    return (
        <PageContainer
            className="page-container"
            header={{
                breadcrumb: {},
            }}
        >
            <ProTable<Organization>
                actionRef={ref}
                rowKey="id"
                search={false}
                options={{
                    search: {
                        placeholder: "输入组织名称查询",
                        style: {width: 400},
                    },
                    fullScreen: true,
                }}
                toolBarRender={() => [
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
                                onConfirm={() => onDeleteOrganizations(selectedRowKeys as number[])}
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
                        const {keyword} = params;
                        const result = await queryOrganizationTreeList(
                            {
                                keyword,
                                ...filter
                            }
                        );
                        return {
                            data: result,
                            success: true,
                        };
                    }
                }
                defaultExpandedRowKeys={[1]}
                rowSelection={{}}
                pagination={false}
            />
        </PageContainer>
    )
};

export default OrganizationListPage;