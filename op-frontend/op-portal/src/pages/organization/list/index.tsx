import React, {FC, useRef} from "react";
import {useHistory} from "react-router-dom";
import {Button, Popconfirm, Space} from "antd";
import {ExportOutlined, MinusOutlined} from "@ant-design/icons";
import {PageContainer} from "@ant-design/pro-layout";
import type {ActionType, ProColumns} from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import {Organization} from "../../../models/Organization";
import {deleteOrganizations, queryOrganizationTreeList} from "../../../services/organization";
import Authority from "../../../components/Authority";
import AuthTableDropDown from "../../../components/AuthTableDropdown";

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
                <Authority value="organization_save">
                    <a key="edit" onClick={() => history.push(`/admin/organization/edit/${record.id}`)}>
                        编辑
                    </a>
                </Authority>,
                <Authority value="organization_delete">
                    <Popconfirm
                        title="确定要删除吗？"
                        okText="确定"
                        cancelText="取消"
                        onConfirm={() => onDeleteOrganizations([record.id] as number[])}
                    >
                        <a key="delete">
                            删除
                        </a>
                    </Popconfirm>
                </Authority>,
                <Authority value="organization_view">
                    <a key="view" onClick={() => history.push(`/admin/organization/detail/${record.id}`)}>
                        查看
                    </a>
                </Authority>,
                <Authority value="organization_save">
                    <a key="addChild" onClick={() => history.push(`/admin/organization/edit`, {
                        pid: record.id,
                    })}>
                        新增下级
                    </a>
                </Authority>,
                <AuthTableDropDown
                    key="actions"
                    menus={[
                        {
                            // @ts-ignore
                            authority: 'organization_authorization',
                            key: 'assignRoles',
                            name: '分配角色',
                            onClick: () => history.push(`/admin/organization/assign-roles/${record.id}`),
                        },
                        {
                            // @ts-ignore
                            authority: 'organization_authorization',
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
                    <Authority value="organization_export">
                        <Button key="button" icon={<ExportOutlined/>}>
                            导出
                        </Button>
                    </Authority>,
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
