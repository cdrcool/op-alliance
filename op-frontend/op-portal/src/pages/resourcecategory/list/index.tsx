import React from 'react';
import ProCard from '@ant-design/pro-card';
import {Button, Card, Input, Pagination, Popconfirm, Space} from "antd";
import * as Icon from "@ant-design/icons";
import {PageContainer} from "@ant-design/pro-layout";
import {ResourceCategory} from "../../../models/ResourceCategory";
import {useHistory} from "react-router-dom";

const {Meta} = Card;
const {Search} = Input;

export default () => {
    const history = useHistory();

    const dataSource: ResourceCategory[] = [
        {
            id: 1,
            categoryName: "管理中心",
            categoryIcon: "TeamOutlined",
            resources: ["组织管理", "用户管理", "角色管理", "资源管理", "菜单管理"]
        },
        {
            id: 2,
            categoryName: "统计中心",
            categoryIcon: "AreaChartOutlined",
        },
        {
            id: 3,
            categoryName: "日志中心",
            categoryIcon: "DatabaseOutlined",
        },
        {
            id: 4,
            categoryName: "监控中心",
            categoryIcon: "PictureOutlined",
        },
        {
            id: 5,
            categoryName: "附件中心",
            categoryIcon: "FileImageOutlined",
        },
        {
            id: 6,
            categoryName: "文档中心",
            categoryIcon: "ReadOutlined",
        },
        {
            id: 7,
            categoryName: "日志中心",
            categoryIcon: "DatabaseOutlined",
        },
        {
            id: 8,
            categoryName: "监控中心",
            categoryIcon: "PictureOutlined",
        },
        {
            id: 9,
            categoryName: "附件中心",
            categoryIcon: "FileImageOutlined",
        },
        {
            id: 10,
            categoryName: "文档中心",
            categoryIcon: "ReadOutlined",
        },
    ];

    const onDeleteResourceCategory = (ids: number[]) => {

    };

    return (
        <PageContainer
            className="page-container"
            header={{
                breadcrumb: {},
            }}
        >
            <ProCard
                wrap={true}
                gutter={[16, 16]}
                extra={
                    <Space style={{float: 'right', marginBottom: 8}}>
                        <Search placeholder="输入资源分类名称查询" style={{width: 400}}/>
                        <Button type="primary">新建</Button>
                    </Space>
                }
                actions={[<Pagination/>]}
            >
                {
                    dataSource.map(item =>
                        <ProCard
                            key={item.id}
                            colSpan={12}
                            bordered
                            hoverable
                            title={item.categoryName}
                            extra={
                                <Space>
                                    <a>编辑</a>
                                    <Popconfirm
                                        title="确定要删除吗？"
                                        okText="确定"
                                        cancelText="取消"
                                        onConfirm={() => onDeleteResourceCategory([item.id] as number[])}
                                    >
                                        <a>删除</a>
                                    </Popconfirm>
                                </Space>
                            }
                            style={{cursor: 'default'}}
                        >
                            <Meta
                                avatar={item.categoryIcon && React.createElement(
                                    // @ts-ignore
                                    Icon[item.categoryIcon],
                                    {
                                        style: {fontSize: '32px', color: 'DodgerBlue'},
                                        onClick: () => history.push(`/admin/resource`, {
                                            categoryId: item.id,
                                        })
                                    }
                                )}
                                description={item.resources && item.resources.join(' | ')}
                            />
                        </ProCard>
                    )
                }
            </ProCard>
        </PageContainer>
    );
};