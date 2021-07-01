import React, {FC, useState} from "react";
import {Alert, Button, Card, Col, Avatar, List, Skeleton, Row, Select, Space, Table, Tag} from "antd";
import {SettingOutlined, EditOutlined, EllipsisOutlined, MinusOutlined, SearchOutlined, UpOutlined} from "@ant-design/icons";

import './index.css';

const { Meta } = Card;

const ResourcePage: FC = () => {

    return (
            <>
                <Card className="card">
                    <Row gutter={16}>
                        <Col span={8}>
                            <Card title="管理中心" hoverable={true}
                                  extra={
                                      <Space>
                                          <Button icon={<EditOutlined/>} type="link">修改</Button>
                                          <Button icon={<MinusOutlined/>} type="link">删除</Button>
                                      </Space>
                                  }
                            >
                                <Meta
                                    avatar={<Avatar src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png" />}
                                    title="Card title"
                                    description="This is the description"
                                />
                            </Card>
                        </Col>
                        <Col span={8}>
                            <Card title="统计中心" hoverable={true}
                                  extra={
                                      <Space>
                                          <Button icon={<EditOutlined/>} type="link">修改</Button>
                                          <Button icon={<MinusOutlined/>} type="link">删除</Button>
                                      </Space>
                                  }
                            >
                                <Meta
                                    avatar={<Avatar src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png" />}
                                    title="Card title"
                                    description="This is the description"
                                />
                            </Card>
                        </Col>
                        <Col span={8}>
                            <Card title="日志中心" hoverable={true}
                                  extra={
                                      <Space>
                                          <Button icon={<EditOutlined/>} type="link">修改</Button>
                                          <Button icon={<MinusOutlined/>} type="link">删除</Button>
                                      </Space>
                                  }
                              >
                                <Meta
                                    avatar={<Avatar src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png" />}
                                    title="Card title"
                                    description="This is the description"
                                />
                            </Card>
                        </Col>
                    </Row>
                </Card>
                <Card className="card">
                    <Row gutter={16}>
                        <Col span={8}>
                            <Card title="监控中心" hoverable={true}
                                  extra={
                                      <Space>
                                          <Button icon={<EditOutlined/>} type="link">修改</Button>
                                          <Button icon={<MinusOutlined/>} type="link">删除</Button>
                                      </Space>
                                  }
                            >
                                <Meta
                                    avatar={<Avatar src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png" />}
                                    title="Card title"
                                    description="This is the description"
                                />
                            </Card>
                        </Col>
                        <Col span={8}>
                            <Card title="附件中心" hoverable={true}
                                  extra={
                                      <Space>
                                          <Button icon={<EditOutlined/>} type="link">修改</Button>
                                          <Button icon={<MinusOutlined/>} type="link">删除</Button>
                                      </Space>
                                  }
                            >
                                <Meta
                                    avatar={<Avatar src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png" />}
                                    title="Card title"
                                    description="This is the description"
                                />
                            </Card>
                        </Col>
                        <Col span={8}>
                            <Card title="文档中心" hoverable={true}
                                  extra={
                                      <Space>
                                          <Button icon={<EditOutlined/>} type="link">修改</Button>
                                          <Button icon={<MinusOutlined/>} type="link">删除</Button>
                                      </Space>
                                  }
                            >
                                <Meta
                                    avatar={<Avatar src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png" />}
                                    title="Card title"
                                    description="This is the description"
                                />
                            </Card>
                        </Col>
                    </Row>
                </Card>
            </>
    )
};

export default ResourcePage;