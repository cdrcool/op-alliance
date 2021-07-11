import React from 'react';
import ProCard from '@ant-design/pro-card';
import {Button, Card, Space} from "antd";
import {TeamOutlined} from "@ant-design/icons";

const {Meta} = Card;

export default () => {
    const dataSource = [
        {
            categoryName: "管理中心",
            categoryIcon: "TeamOutlined",
        },
        {
            categoryName: "统计中心",
            categoryIcon: "TeamOutlined",
        },
    ];
    return (
        <>
            <ProCard ghost wrap gutter={[8, 8]}>
                <ProCard colSpan={8} bordered hoverable
                         title="管理中心" extra={
                    <Space>
                        <Button type="primary" size="small">编辑</Button>
                        <Button size="small">删除</Button>
                    </Space>
                }>
                    <Meta
                        avatar={<TeamOutlined style={{fontSize: '32px', color: 'DodgerBlue'}}/>}
                        description="组织管理 | 用户管理 | 角色管理 | 资源管理 | 菜单管理"
                    />
                </ProCard>
                <ProCard colSpan={8} bordered hoverable>
                    colSpan - 8
                </ProCard>
                <ProCard colSpan={8} bordered hoverable>
                    colSpan - 8
                </ProCard>
                <ProCard colSpan={8} bordered hoverable>
                    colSpan - 8
                </ProCard>
                <ProCard colSpan={8} bordered hoverable>
                    colSpan - 8
                </ProCard>
                <ProCard colSpan={8} bordered hoverable>
                    colSpan - 8
                </ProCard>
            </ProCard>
        </>
    );
};