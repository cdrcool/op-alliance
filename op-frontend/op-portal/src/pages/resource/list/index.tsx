import React, {FC, useState} from "react";
import {Alert, Button, Card, Col, Avatar, List, Skeleton, Row, Select, Space, Table, Tag} from "antd";
import {SettingOutlined, EditOutlined, EllipsisOutlined, MinusOutlined, SearchOutlined, UpOutlined} from "@ant-design/icons";

import './index.css';

const ResourcePage: FC = () => {
    const dataSource = [
        {
            id: '1',
            title: '管理中心',
        },
        {
            id: '2',
            title: '统计中心',
        },
        {
            id: '3',
            title: '日志中心',
        },
        {
            id: '4',
            title: '监控中心',
        },
        {
            id: '5',
            title: '文档中心',
        },
    ];

    return (
            <Card className="card">
                <Row gutter={16}>
                    <Col span={8}>
                        <Card title="Card title" bordered={false}>
                            Card content
                        </Card>
                    </Col>
                    <Col span={8}>
                        <Card title="Card title" bordered={false}>
                            Card content
                        </Card>
                    </Col>
                    <Col span={8}>
                        <Card title="Card title" bordered={false}>
                            Card content
                        </Card>
                    </Col>
                </Row>
            </Card>
    )
};

export default ResourcePage;