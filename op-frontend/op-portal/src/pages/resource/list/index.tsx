import React, {FC, useEffect, useRef, useState} from "react";
import {useHistory} from "react-router-dom";
import {Button, Popconfirm, Space} from "antd";
import {ExportOutlined, MinusOutlined, PlusOutlined} from "@ant-design/icons";
import {PageContainer} from "@ant-design/pro-layout";
import type {ActionType, ProColumns} from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import {Resource} from "../../../models/Resource";
import {SelectOptions} from "../../../models/SelectOptions";
import {deleteResources, queryResourcePage} from "../../../services/resource";
import {queryResourceCategorySelectOptions} from "../../../services/resourceCategory";
import Authority from "../../../components/Authority";

const ResourceListPage: FC = () => {
        const history = useHistory();
        // @ts-ignore
        const {categoryId} = history.location.state || {};
        const ref = useRef<ActionType>();

        const [categoryOptions, setCategoryOptions] = useState<SelectOptions[]>([]);

        useEffect(() => {
            const fetchData = async () => {
                const categoryOptions = await queryResourceCategorySelectOptions({});
                setCategoryOptions(categoryOptions || []);
            }

            fetchData().then(() => {
            });
        }, []);

        const onDeleteResources = (ids: number[]) => {
            // @ts-ignore
            deleteResources(ids).then(() => ref.current.reloadAndRest());
        };

        const columns: ProColumns<Resource>[] = [
            {
                title: '资源名',
                dataIndex: 'resourceName',
                search: false,
            },
            {
                title: '资源路径',
                dataIndex: 'resourcePath',
                search: false,
            },
            {
                title: '资源描述',
                dataIndex: 'resourceDesc',
                search: false,
            },
            {
                title: '资源分类',
                dataIndex: 'categoryId',
                valueType: 'select',
                fieldProps: {
                    options: categoryOptions,
                },
                initialValue: categoryId,
            },
            {
                title: '操作',
                valueType: 'option',
                render: (text, record, _, action) => [
                    <Authority value="resource_save">
                        <a key="edit" onClick={() => history.push(`/admin/resource/edit/${record.id}`)}>
                            编辑
                        </a>
                    </Authority>,
                    <Authority value="resource_delete">
                        <Popconfirm
                            title="确定要删除吗？"
                            okText="确定"
                            cancelText="取消"
                            onConfirm={() => onDeleteResources([record.id] as number[])}
                        >
                            <a key="delete">
                                删除
                            </a>
                        </Popconfirm>
                    </Authority>,
                    <Authority value="resource_view">
                        <a key="view" onClick={() => history.push(`/admin/resource/detail/${record.id}`)}>
                            查看
                        </a>
                    </Authority>,
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
                    options={{
                        search: {
                            placeholder: "输入资源名、资源路径或资源描述查询",
                            style: {width: 400},
                        },
                        fullScreen: true,
                    }}
                    toolBarRender={() => [
                        <Authority value="resource_save">
                            <Button key="button" type="primary" icon={<PlusOutlined/>}
                                    onClick={() => history.push('/admin/resource/edit')}>
                                新建
                            </Button>
                        </Authority>,
                        <Authority value="resource_export">
                            <Button key="button" icon={<ExportOutlined/>}>
                                导出
                            </Button>
                        </Authority>,
                    ]}
                    tableAlertOptionRender={({selectedRowKeys}) => {
                        return (
                            <Space>
                                <Authority value="resource_delete">
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
                                </Authority>
                            </Space>
                        );
                    }}
                    columns={columns}
                    request={
                        async (params, sort, filter) => {
                            const {current, pageSize, ...others} = params;
                            const result = await queryResourcePage(
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
                    expandable={
                        {
                            expandedRowRender: (record) => {
                                return (
                                    <ProTable
                                        columns={[
                                            {title: '动作名称', dataIndex: 'actionName'},
                                            {title: '动作路径', dataIndex: 'actionPath'},
                                            {title: '权限标识', dataIndex: 'permission'},
                                        ]}
                                        headerTitle={false}
                                        search={false}
                                        options={false}
                                        dataSource={record.actions || []}
                                        pagination={false}
                                    />
                                );
                            },
                        }
                    }
                />
            </PageContainer>
        )
    }
;

export default ResourceListPage;
