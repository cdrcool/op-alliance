import React, {FC, useRef} from "react";
import {Button, Popconfirm, Space} from "antd";
import {ExportOutlined, MinusOutlined, PlusOutlined} from "@ant-design/icons";

import type {ActionType, ProColumns} from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import {useHistory} from "react-router-dom";
import {PageContainer} from "@ant-design/pro-layout";
import {Resource} from "../../../models/Resource";
import {deleteResources, queryResourcePage} from "../../../services/resource";

const ResourceListPage: FC = () => {
    const history = useHistory();
    // @ts-ignore
    const {categoryId} = history.location.state || {};
    const ref = useRef<ActionType>();

    const columns: ProColumns<Resource>[] = [
        {
            title: '资源名',
            dataIndex: 'resourceName',
        },
        {
            title: '资源路径',
            dataIndex: 'resourcePath',
        },
        {
            title: '资源描述',
            dataIndex: 'resourceDesc',
        },
        {
            title: '操作',
            valueType: 'option',
            render: (text, record, _, action) => [
                <a key="edit" onClick={() => history.push(`/admin/resource/edit/${record.id}`)}>
                    编辑
                </a>,
                <Popconfirm
                    title="确定要删除吗？"
                    okText="确定"
                    cancelText="取消"
                    onConfirm={() => onDeleteResources([record.id] as number[])}
                >
                    <a key="delete">
                        删除
                    </a>
                </Popconfirm>,
                <a key="view" onClick={() => history.push(`/admin/resource/detail/${record.id}`)}>
                    查看
                </a>,
            ],
        },
    ];

    const onDeleteResources = (ids: number[]) => {
        // @ts-ignore
        deleteResources(ids).then(() => ref.current.reloadAndRest());
    }

    return (
        <PageContainer className="page-container" header={{
            breadcrumb: {},
        }}>
            <ProTable<Resource>
                actionRef={ref}
                rowKey="id"
                search={false}
                options={{
                    search: {
                        placeholder: "输入资源名、资源路径或资源描述查询",
                        style: {width: 400},
                    },
                    fullScreen: true,
                }}
                columns={columns}
                request={
                    async (params, sort, filter) => {
                        const {current, pageSize, keyword} = params;
                        const result = await queryResourcePage(
                            (current || 1) - 1,
                            pageSize || 10,
                            {
                                keyword,
                                ...filter,
                                categoryId
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
                            onClick={() => history.push('/admin/resource/edit', {
                                categoryId,
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
                                onConfirm={() => onDeleteResources(selectedRowKeys as number[])}
                            >
                                <Button key="batchDelete" icon={<MinusOutlined/>}>
                                    批量删除
                                </Button>
                            </Popconfirm>
                        </Space>
                    );
                }}
            />
        </PageContainer>
    )
};

export default ResourceListPage;
