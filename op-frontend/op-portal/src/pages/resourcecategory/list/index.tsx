import React, {FC, useRef} from 'react';
import {useHistory} from "react-router-dom";
import {Button, message, Popconfirm, Tag} from 'antd';
import * as Icon from "@ant-design/icons";
import {ExportOutlined} from "@ant-design/icons";
import {PageContainer} from "@ant-design/pro-layout";
import ProList from '@ant-design/pro-list';
import {ActionType} from "@ant-design/pro-table";
import {ResourceCategory} from "../../../models/ResourceCategory";
import {deleteResourceCategories, queryResourceCategoryPage} from "../../../services/resourceCategory";
import {initResourcePathPermissions} from "../../../services/resourceAction";
import Authority from "../../../components/Authority";

const ResourceCategoryListPage: FC = () => {
    const history = useHistory();
    const ref = useRef<ActionType>();

    const onDeleteResourceCategory = (ids: number[]) => {
        // @ts-ignore
        deleteResourceCategories(ids).then(() => ref.current.reloadAndRest());
    };

    return (
        <PageContainer
            className="page-container"
            header={{
                breadcrumb: {},
            }}
        >
            <ProList<ResourceCategory>
                style={{backgroundColor: '#fff'}}
                grid={{gutter: 16, column: 2}}
                actionRef={ref}
                rowKey="id"
                options={{
                    search: {
                        placeholder: "输入资源分类名称查询",
                        style: {width: 400},
                    },
                    fullScreen: true,
                    setting: false,
                }}
                toolBarRender={() => {
                    return [
                        <Authority value="resource_category_save">
                            <Button key="add" type="primary"
                                    onClick={() => history.push('/admin/resourceCategory/edit')}>
                                新建
                            </Button>
                        </Authority>,
                        <Authority value="resource_category_export">
                            <Button key="button" icon={<ExportOutlined/>}>
                                导出
                            </Button>
                        </Authority>,
                        <Authority value="resource_category_save">
                            <Button key="refresh"
                                    onClick={() => initResourcePathPermissions().then(() => message.success("初始化资源路径权限关联成功"))}>
                                初始化资源路径权限关联
                            </Button>
                        </Authority>,
                    ];
                }}
                metas={{
                    avatar: {
                        dataIndex: 'categoryIcon',
                        render: (_, row) => {
                            return (
                                row.categoryIcon ? React.createElement(
                                    // @ts-ignore
                                    Icon[row.categoryIcon],
                                    {
                                        style: {color: '#444950'},
                                    }
                                ) : <></>
                            )
                        },
                    },
                    title: {
                        title: '分类名称',
                        dataIndex: 'categoryName',
                        render: (_, row) => {
                            return (
                                <a onClick={() => history.push('/admin/resource', {categoryId: row.id})}>{row.categoryName}</a>
                            )
                        },
                    },
                    subTitle: {
                        dataIndex: 'serverName',
                        render: (_, row) => {
                            return (
                                <Tag color="#5BD8A6" key={row.serverName}>
                                    {row.serverName}
                                </Tag>
                            );
                        },
                    },
                    type: {},
                    actions: {
                        render: (text, row) => [
                            <Authority value="resource_category_save">
                                <a onClick={() => history.push(`/admin/resourceCategory/edit/${row.id}`)}>编辑</a>
                            </Authority>,
                            <Authority value="resource_category_delete">
                                <Popconfirm
                                    title="确定要删除吗？"
                                    okText="确定"
                                    cancelText="取消"
                                    onConfirm={() => onDeleteResourceCategory([row.id] as number[])}
                                >
                                    <a>删除</a>
                                </Popconfirm>
                            </Authority>,
                            <Authority value="resource_category_view">
                                <a onClick={() => history.push(`/admin/resourceCategory/detail/${row.id}`)}>查看</a>
                            </Authority>,
                        ],
                    },
                    content: {
                        dataIndex: 'resourceNames',
                        render: (text, row) => {
                            return (
                                <>{row.resourceNames?.join(' | ')}</>
                            );
                        },
                    },
                }}
                request={
                    async (params, sort, filter) => {
                        const {current, pageSize, ...others} = params;
                        const result = await queryResourceCategoryPage(
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
                    defaultPageSize: 10,
                }}
            />
        </PageContainer>
    );
};

export default ResourceCategoryListPage;
