import React, {FC, useState} from "react";
import {Alert, Breadcrumb, Button, Card, Col, Divider, Form, Input, Row, Select, Space, Table, Tag} from "antd";
import {
    ArrowLeftOutlined,
    DownOutlined,
    ExportOutlined,
    MinusOutlined,
    PlusOutlined,
    SearchOutlined,
    UpOutlined
} from "@ant-design/icons";

const {Option} = Select;

const UserPage: FC = () => {
    const [expand, setExpand] = useState<boolean>(false);

    const [form] = Form.useForm();

    const columns = [
       /* {
            title: '编号',
            dataIndex: 'userNo',
            key: 'userNo',
        },*/
        {
            title: '用户名',
            dataIndex: 'username',
            key: 'username',
            render: (value: string) => <a>{value}</a>,
        },
        {
            title: '昵称',
            dataIndex: 'nickname',
            key: 'nickname',
        },
        {
            title: '手机号',
            dataIndex: 'phone',
            key: 'phone',
        },
        {
            title: '邮箱',
            dataIndex: 'email',
            key: 'email',
        },
        {
            title: '性别',
            dataIndex: 'gender',
            key: 'gender',
            render: (value: number) => value === 1 ? '男' : '女',
        },
        {
            title: '出生日期',
            dataIndex: 'birthday',
            key: 'birthday',
        },
        {
            title: '帐号状态',
            dataIndex: 'status',
            key: 'status',
            render: (value: number) => <Tag color={value === 1 ? "green" : "red"}>
                {value === 0 ? '禁用' : (value === 1 ? '启用' : (value === 2 ? '过期' : (value === 3 ? '锁定' : '密码过期')))}
            </Tag>,
        },
        {
            title: '组织',
            dataIndex: 'orgName',
            key: 'orgName',
        },
        {
            title: '操作',
            key: 'action',
            render: (text: string, record: object) => (
                <Space>
                    <a>编辑</a>
                    <a>删除</a>
                </Space>
            ),
        },
    ];

    const dataSource = [
        {
            id: '1',
            userNo: '1',
            username: 'admin',
            nickname: '管理员',
            phone: '13627200350',
            email: 'admin@163.com',
            gender: 1,
            birthday: '1989-09-15',
            status: 1,
            orgName: '一公司',
        },
        {
            id: '2',
            userNo: '2',
            username: 'guest',
            nickname: '游客',
            phone: '13669064460',
            email: 'guest@126.com',
            gender: 2,
            birthday: '1990-01-02',
            status: 1,
            orgName: '二公司',
        },
        {
            id: '3',
            userNo: '3',
            username: 'limeng',
            nickname: '李萌',
            phone: '13669064460',
            email: 'limeng@qq.com',
            gender: 2,
            birthday: '1989-04-24',
            status: 1,
            orgName: '三公司',
        },
        {
            id: '4',
            userNo: '4',
            username: 'admin',
            nickname: '管理员',
            phone: '13627200350',
            email: 'admin@163.com',
            gender: 1,
            birthday: '1989-09-15',
            status: 1,
            orgName: '一公司',
        },
        {
            id: '5',
            userNo: '5',
            username: 'guest',
            nickname: '游客',
            phone: '13669064460',
            email: 'guest@126.com',
            gender: 2,
            birthday: '1990-01-02',
            status: 1,
            orgName: '二公司',
        },
        {
            id: '6',
            userNo: '6',
            username: 'limeng',
            nickname: '李萌',
            phone: '13669064460',
            email: 'limeng@qq.com',
            gender: 2,
            birthday: '1989-04-24',
            status: 1,
            orgName: '三公司',
        },
        {
            id: '7',
            userNo: '7',
            username: 'admin',
            nickname: '管理员',
            phone: '13627200350',
            email: 'admin@163.com',
            gender: 1,
            birthday: '1989-09-15',
            status: 1,
            orgName: '一公司',
        },
        {
            id: '8',
            userNo: '8',
            username: 'guest',
            nickname: '游客',
            phone: '13669064460',
            email: 'guest@126.com',
            gender: 2,
            birthday: '1990-01-02',
            status: 1,
            orgName: '二公司',
        },
        {
            id: '9',
            userNo: '9',
            username: 'limeng',
            nickname: '李萌',
            phone: '13669064460',
            email: 'limeng@qq.com',
            gender: 2,
            birthday: '1989-04-24',
            status: 1,
            orgName: '三公司',
        },
        {
            id: '10',
            userNo: '10',
            username: 'admin',
            nickname: '管理员',
            phone: '13627200350',
            email: 'admin@163.com',
            gender: 1,
            birthday: '1989-09-15',
            status: 1,
            orgName: '一公司',
        },
        {
            id: '11',
            userNo: '11',
            username: 'guest',
            nickname: '游客',
            phone: '13669064460',
            email: 'guest@126.com',
            gender: 2,
            birthday: '1990-01-02',
            status: 1,
            orgName: '二公司',
        },
        {
            id: '12',
            userNo: '12',
            username: 'limeng',
            nickname: '李萌',
            phone: '13669064460',
            email: 'limeng@qq.com',
            gender: 2,
            birthday: '1989-04-24',
            status: 1,
            orgName: '三公司',
        },
    ];

    return (
        <>
            <Card size="small" className="card">
                <Space className="breadcrumb">
                    <ArrowLeftOutlined/>
                    <Breadcrumb>
                        <Breadcrumb.Item>管理中心</Breadcrumb.Item>
                        <Breadcrumb.Item>用户管理</Breadcrumb.Item>
                    </Breadcrumb>
                </Space>

                <div style={{float: 'right', marginBottom: 4}}>
                    <Space>
                        <Input placeholder="输入用户名、昵称、手机号或邮箱查询" suffix={<SearchOutlined/>} allowClear={true} style={{width: 400}}/>
                        <Button icon={expand ? <UpOutlined/> : <DownOutlined/>}
                                onClick={() => {setExpand(!expand)}}>{expand ? '收起筛选' : '展开筛选'}
                        </Button>
                    </Space>
                </div>

                {
                    expand ?
                        <>
                            <Divider/>
                            <Form form={form}>
                                <Row gutter={24}>
                                    <Col span={8}>
                                        <Form.Item label="组织" name="orgId">
                                            <Select style={{width: 200}}>
                                                <Option value="1">一公司</Option>
                                                <Option value="2">二公司</Option>
                                                <Option value="3">三公司</Option>
                                            </Select>
                                        </Form.Item>
                                    </Col>
                                    <Col span={8}>
                                        <Form.Item label="性别" name="gender">
                                            <Select style={{width: 200}}>
                                                <Option value="1">男</Option>
                                                <Option value="2">女</Option>
                                            </Select>
                                        </Form.Item>
                                    </Col>
                                    <Col span={8}>
                                        <Form.Item label="状态" name="status">
                                            <Select style={{width: 200}}>
                                                <Option value="0">禁用</Option>
                                                <Option value="1">启用</Option>
                                                <Option value="2">过期</Option>
                                                <Option value="3">锁定</Option>
                                                <Option value="4">密码过期</Option>
                                            </Select>
                                        </Form.Item>
                                    </Col>
                                </Row>
                                <Row>
                                    <Col span={24} style={{textAlign: 'right'}}>
                                        <Space>
                                            <Button type="primary" htmlType="submit">
                                                查询
                                            </Button>
                                            <Button onClick={() => {form.resetFields()}}>
                                                重置
                                            </Button>
                                        </Space>
                                    </Col>
                                </Row>
                            </Form>
                        </> : null
                }
            </Card>

            <Card className="card">
                <div>
                    <Space style={{float: 'right'}}>
                        <Button key="add" type="primary" icon={<PlusOutlined/>}>
                            新增
                        </Button>
                        <Button key="export" icon={<ExportOutlined/>}>
                            导出
                        </Button>
                    </Space>

                    <Space style={{marginBottom: 6}}>
                        <Alert message="已选择 1 项" type="info" style={{width: 400, height: 32}}
                               action={<Button size="small" type="link">
                                   取消选择
                               </Button>}/>
                        <Button key="batchDelete" icon={<MinusOutlined/>}>
                            批量删除
                        </Button>
                    </Space>
                </div>

                <Table columns={columns} dataSource={dataSource}
                   rowKey="id"
                    rowSelection={{
                        type: "checkbox",
                        onChange: (selectedRowKeys: React.Key[], selectedRows: any[]) => {
                            console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
                        }
                    }}
                />
            </Card>
        </>
    )
};

export default UserPage;