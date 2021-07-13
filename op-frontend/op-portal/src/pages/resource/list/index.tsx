import React, {FC, useEffect, useRef, useState} from "react";
import {Button, Popconfirm, Space} from "antd";
import {ExportOutlined, MinusOutlined, PlusOutlined} from "@ant-design/icons";
import type {ActionType, ProColumns} from '@ant-design/pro-table';
import ProTable, {EditableProTable} from '@ant-design/pro-table';
import {useHistory} from "react-router-dom";
import {PageContainer} from "@ant-design/pro-layout";
import {Resource} from "../../../models/Resource";
import {deleteResources, queryResourcePage} from "../../../services/resource";
import {queryResourceCategorySelectList} from "../../../services/resourceCategory";
import {SelectOptions} from "../../../models/SelectOptions";
import {deleteResourceAction, saveResourceAction} from "../../../services/resourceAction";
import {ResourceAction} from "../../../models/ResourceAction";

type ResourceActionListProps = {
    resourceId: number;
    actions: ResourceAction[];
};

const ResourceActionList: React.FC<ResourceActionListProps> = (props) => {
    const {resourceId, actions} = props;
    const [dataSource, setDataSource] = useState<ResourceAction[]>(actions || []);

    useEffect(() => {
        setDataSource(actions);
    }, [actions]);

    return (
        <EditableProTable<ResourceAction>
            rowKey="id"
            columns={[
                {title: '动作名称', dataIndex: 'actionName'},
                {title: '动作路径', dataIndex: 'actionPath'},
                {title: '动作描述', dataIndex: 'actionDesc'},
                {title: '权限标识', dataIndex: 'permission'},
                {title: '动作编号', dataIndex: 'actionNo'},
                {
                    title: '操作',
                    valueType: 'option',
                    render: (text, record, _, action) => [
                        <a key="edit" onClick={
                            () => {
                                action?.startEditable?.(record.id as number)
                            }
                        }>
                            编辑
                        </a>,
                        <Popconfirm
                            title="确定要删除吗？"
                            okText="确定"
                            cancelText="取消"
                            onConfirm={() => {
                                deleteResourceAction(record.id as number).then(() => {
                                    setDataSource(dataSource.filter((item) => item.id !== record.id));
                                });
                            }}
                        >
                            <a key="delete">
                                删除
                            </a>
                        </Popconfirm>,
                    ],
                },
            ]}
            editable={
                {
                    type: "single",
                    onSave: async (rowKey, record, originRow, newLineConfig) => {
                        if (record.isAdd) {
                            delete record.id;
                        }
                        const id = await saveResourceAction(record);
                        if (record.isAdd) {
                            setDataSource([...dataSource, record]);
                        } else {
                            setDataSource(dataSource.map(item => item.id === rowKey ? {...record, id} : item));
                        }
                    },
                    onDelete: async (key, row) => {
                        await deleteResourceAction(key as number);
                        setDataSource(dataSource.filter((item) => item.id !== key));
                    }
                }
            }
            recordCreatorProps={
                {
                    record: {
                        // @ts-ignore
                        id: Math.max(...dataSource.map(item => item.id)) + 1,
                        resourceId,
                        isAdd: true,
                    },
                    creatorButtonText: '新增资源动作',
                }
            }

            value={dataSource}
        />
    )
};

const ResourceListPage: FC = () => {
    const history = useHistory();
    // @ts-ignore
    const {categoryId} = history.location.state || {};
    const ref = useRef<ActionType>();

    const [categoryOptions, setCategoryOptions] = useState<SelectOptions[]>([]);

    useEffect(() => {
        const fetchData = async () => {
            const categoryOptions = await queryResourceCategorySelectList({});
            setCategoryOptions(categoryOptions || []);
        }

        fetchData().then(() => {
        });
    }, []);

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
                columns={columns}
                expandable={
                    {
                        expandedRowRender: (resource, index, indent, expanded) => {
                            return (
                                <ResourceActionList resourceId={resource.id as number} actions={resource.actions || []}/>
                            );
                        },
                    }
                }
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
                pagination={{
                    pageSize: 10,
                }}
                rowSelection={{}}
                toolBarRender={() => [
                    <Button key="button" type="primary" icon={<PlusOutlined/>}
                            onClick={() => history.push('/admin/resource/edit')}>
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
