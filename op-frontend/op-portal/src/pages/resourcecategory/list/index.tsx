import React, {FC} from "react";
import {useHistory} from "react-router-dom";
import {Button, Card, Col, Input, Pagination, Row, Space} from "antd";
import {
    AreaChartOutlined,
    DatabaseOutlined,
    FileImageOutlined,
    PictureOutlined,
    ReadOutlined,
    SearchOutlined,
    TeamOutlined
} from "@ant-design/icons";

import './index.css';

const {Meta} = Card;

const ResourceCategoryListPage: FC = () => {
    const history = useHistory();

    return (
        <>
            <Card size="small" className="card">
                <div style={{float: 'right', marginBottom: 4}}>
                    <Space>
                        <Input placeholder="输入资源分类名称查询" suffix={<SearchOutlined/>} allowClear={true}
                               style={{width: 400}}/>
                    </Space>
                </div>
            </Card>

            <Card className="card">
                <Row>
                    <Col span={8}>
                        <Card title="管理中心" hoverable={true} className="card"
                              extra={
                                  <>
                                      <Button type="link" size="small">编辑</Button>
                                      <Button type="link" size="small">删除</Button>
                                  </>
                              }
                              onClick={() => {
                                  history.push('/management/resource')
                              }}
                        >
                            <Meta
                                avatar={<TeamOutlined style={{fontSize: '32px', color: 'DodgerBlue'}}/>}
                                description="组织管理 | 用户管理 | 角色管理 | 资源管理 | 菜单管理"
                            />
                        </Card>
                    </Col>
                    <Col span={8}>
                        <Card title="统计中心" hoverable={true} className="card" extra={
                            <>
                                <Button type="link" size="small">编辑</Button>
                                <Button type="link" size="small">删除</Button>
                            </>
                        }>
                            <Meta
                                avatar={<AreaChartOutlined style={{fontSize: '32px', color: 'DodgerBlue'}}/>}
                                description="组织管理 | 用户管理 | 角色管理 | 资源管理 | 菜单管理"
                            />
                        </Card>
                    </Col>
                    <Col span={8}>
                        <Card title="日志中心" hoverable={true} className="card" extra={
                            <>
                                <Button type="link" size="small">编辑</Button>
                                <Button type="link" size="small">删除</Button>
                            </>
                        }>
                            <Meta
                                avatar={<DatabaseOutlined style={{fontSize: '32px', color: 'DodgerBlue'}}/>}
                                description="组织管理 | 用户管理 | 角色管理 | 资源管理 | 菜单管理"
                            />
                        </Card>
                    </Col>
                    <Col span={8}>
                        <Card title="监控中心" hoverable={true} className="card" extra={
                            <>
                                <Button type="link" size="small">编辑</Button>
                                <Button type="link" size="small">删除</Button>
                            </>
                        }>
                            <Meta
                                avatar={<PictureOutlined style={{fontSize: '32px', color: 'DodgerBlue'}}/>}
                                description="组织管理 | 用户管理 | 角色管理 | 资源管理 | 菜单管理"
                            />
                        </Card>
                    </Col>
                    <Col span={8}>
                        <Card title="附件中心" hoverable={true} className="card" extra={
                            <>
                                <Button type="link" size="small">编辑</Button>
                                <Button type="link" size="small">删除</Button>
                            </>
                        }>
                            <Meta
                                avatar={<FileImageOutlined style={{fontSize: '32px', color: 'DodgerBlue'}}/>}
                                description="组织管理 | 用户管理 | 角色管理 | 资源管理 | 菜单管理"
                            />
                        </Card>
                    </Col>
                    <Col span={8}>
                        <Card title="文档中心" hoverable={true} className="card" extra={
                            <>
                                <Button type="link" size="small">编辑</Button>
                                <Button type="link" size="small">删除</Button>
                            </>
                        }>
                            <Meta
                                avatar={<ReadOutlined style={{fontSize: '32px', color: 'DodgerBlue'}}/>}
                                description="组织管理 | 用户管理 | 角色管理 | 资源管理 | 菜单管理"
                            />
                        </Card>
                    </Col>
                </Row>
                <Pagination total={50} style={{float: 'right', right: 0, marginTop: 8}}/>
            </Card>
        </>
    )
};

export default ResourceCategoryListPage;