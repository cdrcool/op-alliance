import React, {useRef, useState} from 'react';
import {Button, Input, Popconfirm, Tag} from 'antd';
import ProList from '@ant-design/pro-list';
import {PageContainer} from "@ant-design/pro-layout";
import * as Icon from "@ant-design/icons";
import {deleteResourceCategories, queryResourceCategoryPage} from "../../../services/resourceCategory";
import {ActionType} from "@ant-design/pro-table";
import {useHistory} from "react-router-dom";

const {Search} = Input;

const ResourceCategoryListPage: React.FC = () => {
    const history = useHistory();
    const ref = useRef<ActionType>();

    const [keyword, setKeyword] = useState<string>();

    const onSearch = (value: string) => {
        setKeyword(value);
        // @ts-ignore
        ref.current.reloadAndRest();
    };

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
            <ProList<any>
                actionRef={ref}
                style={{backgroundColor: '#fff'}}
                grid={{gutter: 16, column: 2}}
                toolBarRender={() => {
                    return [
                        <Search style={{width: 400}} placeholder="输入资源分类名称查询" allowClear onSearch={(value) =>onSearch(value)}/>,
                        <Button key="add" type="primary" onClick={() => history.push('/admin/resourceCategory/edit')}>
                            新建
                        </Button>,
                    ];
                }}
                request={
                    async (params, sort, filter) => {
                        const {current, pageSize} = params;
                        const result = await queryResourceCategoryPage(
                            (current || 1) - 1,
                            pageSize || 10,
                            {
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
                                <a onClick={() => history.push('/admin/resource')}>{row.categoryName}</a>
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
                            <a onClick={() => history.push(`/admin/resourceCategory/edit/${row.id}`)}>编辑</a>,
                            <Popconfirm
                                title="确定要删除吗？"
                                okText="确定"
                                cancelText="取消"
                                onConfirm={() => onDeleteResourceCategory([row.id] as number[])}
                            >
                                <a>删除</a>
                            </Popconfirm>,
                            <a onClick={() => history.push(`/admin/resourceCategory/detail/${row.id}`)}>查看</a>,
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
                pagination={{
                    defaultPageSize: 8,
                    showSizeChanger: false,
                }}
            />
        </PageContainer>
    );
};

export default ResourceCategoryListPage;