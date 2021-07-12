import React from 'react';
import ProCard from '@ant-design/pro-card';
import {Button, Card, Pagination, Popconfirm, Space, Input} from "antd";
import * as Icon from "@ant-design/icons";
import {PageContainer} from "@ant-design/pro-layout";
import {ResourceCategory} from "../../../models/Resource";

const {Meta} = Card;
const {Search} = Input;

export default () => {
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
            <Space style={{float: 'right', marginBottom: 8}}>
                <Search placeholder="输入资源分类名称查询" style={{width: 400}}/>
                <Button type="primary">新建</Button>
            </Space>
            <ProCard ghost wrap gutter={[8, 8]}>
                {
                    dataSource.map(item =>
                        <ProCard
                            key={item.id}
                            colSpan={8}
                            bordered
                            hoverable
                            title={item.categoryName}
                            extra={
                                <Space>
                                    <Button size="small">编辑</Button>
                                    <Popconfirm
                                        title="确定要删除吗？"
                                        okText="确定"
                                        cancelText="取消"
                                        onConfirm={() => onDeleteResourceCategory([item.id] as number[])}
                                    >
                                        <Button size="small">删除</Button>
                                    </Popconfirm>
                                </Space>
                            }>
                            <Meta
                                avatar={item.categoryIcon && React.createElement(
                                    // @ts-ignore
                                    Icon[item.categoryIcon],
                                    {
                                        style: {fontSize: '32px', color: 'DodgerBlue'}
                                    }
                                )}
                                description={item.resources && item.resources.join(' | ')}
                            />
                        </ProCard>
                    )
                }
            </ProCard>
            <Pagination style={{float: 'right', marginTop: 8}}/>
        </PageContainer>
    );
};