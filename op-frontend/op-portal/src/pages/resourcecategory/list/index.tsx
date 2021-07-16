import React from 'react';
import {Button, Input, Popconfirm, Tag} from 'antd';
import ProList from '@ant-design/pro-list';
import {PageContainer} from "@ant-design/pro-layout";
import {ResourceCategory} from "../../../models/ResourceCategory";
import * as Icon from "@ant-design/icons";
import {queryResourceCategoryPage} from "../../../services/resourceCategory";

const {Search} = Input;

const dataSource: ResourceCategory[] = [
    {
        id: 1,
        categoryName: "管理中心",
        categoryPath: "admin",
        categoryIcon: "TeamOutlined",
        resources: ["组织管理", "用户管理", "角色管理", "资源管理", "菜单管理"]
    },
    {
        id: 2,
        categoryName: "统计中心",
        categoryPath: "statistics",
        categoryIcon: "AreaChartOutlined",
    },
    {
        id: 3,
        categoryName: "日志中心",
        categoryPath: "logging",
        categoryIcon: "DatabaseOutlined",
    },
    {
        id: 4,
        categoryName: "监控中心",
        categoryPath: "monitor",
        categoryIcon: "PictureOutlined",
    },
    {
        id: 5,
        categoryName: "附件中心",
        categoryPath: "attachment",
        categoryIcon: "FileImageOutlined",
    },
    {
        id: 6,
        categoryName: "文档中心",
        categoryPath: "document",
        categoryIcon: "ReadOutlined",
    },
    {
        id: 7,
        categoryName: "日志中心",
        categoryPath: "logging",
        categoryIcon: "DatabaseOutlined",
    },
    {
        id: 8,
        categoryName: "监控中心",
        categoryPath: "monitor",
        categoryIcon: "PictureOutlined",
    },
    {
        id: 9,
        categoryName: "附件中心",
        categoryPath: "attachment",
        categoryIcon: "FileImageOutlined",
    },
    {
        id: 10,
        categoryName: "文档中心",
        categoryPath: "document",
        categoryIcon: "ReadOutlined",
    },
];

const onDeleteResourceCategory = (ids: number[]) => {

};

const data = dataSource.map((item) => ({
    title: item.categoryName,
    subTitle: <Tag color="#5BD8A6">{item.categoryPath}</Tag>,
    actions: [
        <a>编辑</a>,
        <Popconfirm
            title="确定要删除吗？"
            okText="确定"
            cancelText="取消"
            onConfirm={() => onDeleteResourceCategory([item.id] as number[])}
        >
            <a>删除</a>
        </Popconfirm>
    ],
    avatar: item.categoryIcon && React.createElement(
        // @ts-ignore
        Icon[item.categoryIcon],
        {
            style: {fontSize: '16px', color: '#1890ff'},
        }
    ),
    content: (
        <div
            style={{
                flex: 1,
            }}
        >
            <div
                style={{
                    width: 200,
                }}
            >
                <div>发布中</div>
            </div>
        </div>
    ),
}));

export default () => {
    return (
        <PageContainer
            className="page-container"
            header={{
                breadcrumb: {},
            }}
        >
            <ProList<any>
                style={{backgroundColor: '#fff'}}
                toolBarRender={() => {
                    return [
                        <Search placeholder="输入资源分类名称查询" style={{width: 400}}/>,
                        <Button key="add" type="primary">
                            新建
                        </Button>,
                    ];
                }}
                request={
                    async (params, sort, filter) => {
                        const {current, pageSize, keyword} = params;
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
                pagination={{
                    defaultPageSize: 8,
                    showSizeChanger: false,
                }}
                grid={{gutter: 16, column: 2}}
                metas={{
                    avatar: {
                        dataIndex: 'categoryIcon',
                        render: (_, row) => {
                            return (
                                row.categoryIcon ? React.createElement(
                                    // @ts-ignore
                                    Icon[row.categoryIcon],
                                    {
                                        style: {fontSize: '32px', color: '#1890ff'},
                                    }
                                ) : <></>
                            )
                        },
                    },
                    title: {
                        title: '分类名称',
                        dataIndex: 'categoryName',
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
                            <a>编辑</a>,
                            <Popconfirm
                                title="确定要删除吗？"
                                okText="确定"
                                cancelText="取消"
                                onConfirm={() => onDeleteResourceCategory([row.id] as number[])}
                            >
                                <a>删除</a>
                            </Popconfirm>,
                        ],
                    },
                    content: {
                        dataIndex: 'serverName',
                    },
                }}
            />
        </PageContainer>
);
};