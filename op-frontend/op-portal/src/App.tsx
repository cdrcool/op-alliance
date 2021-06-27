import React, {FC, useState} from 'react';
import {
    Affix,
    Avatar,
    Badge,
    Breadcrumb,
    Button,
    Tooltip,
    Card,
    Col,
    Divider,
    Form,
    Input,
    Layout,
    List,
    Menu,
    Popover,
    Row,
    Select,
    Space,
    Alert,
    Table,
    Tabs,
    Tag,
} from 'antd';
import {
    AreaChartOutlined,
    BellOutlined,
    BookOutlined,
    DatabaseOutlined,
    DesktopOutlined,
    DownOutlined,
    ExportOutlined,
    FileImageOutlined,
    MenuFoldOutlined,
    MenuUnfoldOutlined,
    PictureOutlined,
    PlusOutlined,
    MinusOutlined,
    ReadOutlined,
    SearchOutlined,
    TeamOutlined,
    UpOutlined,
} from '@ant-design/icons';

import './App.css';

const {Header, Sider, Content} = Layout;
const {SubMenu} = Menu;
const {TabPane} = Tabs;
const {Option} = Select;

const App: FC = () => {
    const [collapsed, setCollapsed] = useState<boolean>(false);
    const [expand, setExpand] = useState<boolean>(false);

    const [form] = Form.useForm();

    const info = <Tabs defaultActiveKey="notice" className="info">
        <TabPane tab="通知" key="notice">
            <List
                itemLayout="horizontal"
                dataSource={[
                    {
                        title: 'Ant Design Title 1',
                    },
                    {
                        title: 'Ant Design Title 2',
                    },
                    {
                        title: 'Ant Design Title 3',
                    },
                    {
                        title: 'Ant Design Title 4',
                    },
                ]}
                renderItem={item => (
                    <List.Item>
                        <List.Item.Meta
                            avatar={<Avatar src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"/>}
                            title={<a href="https://ant.design">{item.title}</a>}
                            description="Ant Design, a design language for background applications, is refined by Ant UED Team"
                        />
                    </List.Item>
                )}
            />
        </TabPane>
        <TabPane tab="消息" key="message">
            Content of Tab Pane 2
        </TabPane>
        <TabPane tab="代办" key="todo">
            Content of Tab Pane 3
        </TabPane>
    </Tabs>;

    return (
        <Layout id="root">
            <Sider trigger={null} collapsible collapsed={collapsed}>
                <div className="logo">
                    <a>
                        <img src="logo.svg" alt="logo"/>
                        <h1 style={collapsed ? {display: "none"} : {display: "inline-block"}}>OnePiece</h1>
                    </a>
                </div>
                <Menu theme="dark" mode="inline" defaultSelectedKeys={['workbench']}>
                    <Menu.Item key="workbench" icon={<DesktopOutlined/>}>
                        工作台
                    </Menu.Item>
                    <SubMenu key="management" icon={<TeamOutlined/>} title="管理中心">
                        <Menu.Item key="organization">
                            组织管理
                        </Menu.Item>
                        <Menu.Item key="user">
                            用户管理
                        </Menu.Item>
                        <Menu.Item key="role">
                            角色管理
                        </Menu.Item>
                        <Menu.Item key="resource">
                            资源管理
                        </Menu.Item>
                        <Menu.Item key="menu">
                            菜单管理
                        </Menu.Item>
                    </SubMenu>
                    <Menu.Item key="statistics" icon={<AreaChartOutlined/>}>
                        统计中心
                    </Menu.Item>
                    <Menu.Item key="logging" icon={<DatabaseOutlined/>}>
                        日志中心
                    </Menu.Item>
                    <Menu.Item key="monitor" icon={<PictureOutlined/>}>
                        监控中心
                    </Menu.Item>
                    <Menu.Item key="file" icon={<FileImageOutlined/>}>
                        附件中心
                    </Menu.Item>
                    <Menu.Item key="document" icon={<ReadOutlined/>}>
                        文档中心
                    </Menu.Item>
                </Menu>
            </Sider>
            <Layout className="main">
                <Affix>
                    <Header className="header">
                        {
                            React.createElement(collapsed ? MenuUnfoldOutlined : MenuFoldOutlined, {
                                className: 'trigger',
                                onClick: () => setCollapsed(!collapsed),
                            })
                        }
                        <Breadcrumb className="nav">
                            <Breadcrumb.Item>管理中心</Breadcrumb.Item>
                            <Breadcrumb.Item>用户管理</Breadcrumb.Item>
                        </Breadcrumb>
                        <Menu theme="light" mode="horizontal" className="menu">
                            <Menu.Item key="search" className="search">
                                <Input prefix={<SearchOutlined/>} placeholder="搜索" bordered={false}/>
                            </Menu.Item>
                            <Popover trigger="click" placement="bottomRight" content={info}>
                                <Menu.Item key="info">
                                    <Badge count={12}><BellOutlined style={{fontSize: 18, border: 0}}/></Badge>
                                </Menu.Item>
                            </Popover>
                            <SubMenu key="avatar" icon={
                                <>
                                    <Avatar src="avatar.png"
                                            style={{width: 24, height: 24, position: "relative", top: -3}}/>
                                    <span style={{marginLeft: 6, fontSize: 14}}>管理员</span>
                                </>
                            }>
                                <Menu.Item key="center">
                                    个人中心
                                </Menu.Item>
                                <Menu.Item key="settings">
                                    个人设置
                                </Menu.Item>
                                <Menu.Divider/>
                                <Menu.Item key="logout">
                                    退出登录
                                </Menu.Item>
                            </SubMenu>
                            <Tooltip title="开发文档" color="blue">
                                <Menu.Item key="knowledge">
                                    <BookOutlined/>
                                </Menu.Item>
                            </Tooltip>
                        </Menu>
                    </Header>
                </Affix>
                <Content className="content">
                    <Card size="small" bordered={false} className="card">
                        <div style={{float: 'right', marginBottom: 6}}>
                            <Space>
                                <Input placeholder="输入用户名、昵称、手机号或邮箱查询" suffix={<SearchOutlined/>} allowClear={true}
                                       style={{width: 300}}/>
                                <Button icon={expand ? <UpOutlined/> : <DownOutlined/>}
                                        onClick={() => {
                                            setExpand(!expand)
                                        }}>{expand ? '收起筛选' : '展开筛选'}
                                </Button>
                            </Space>
                        </div>
                        {expand ?
                            <>
                                <Divider/>
                                <Form form={form} className="ant-advanced-search-form">
                                    <Row gutter={24}>
                                        <Col span={8}>
                                            <Form.Item label="组织" name="orgId">
                                                <Select
                                                    style={{width: 200}}
                                                    showSearch
                                                >
                                                    <Option value="1">一公司</Option>
                                                    <Option value="2">二公司</Option>
                                                    <Option value="3">三公司</Option>
                                                </Select>
                                            </Form.Item>
                                        </Col>
                                        <Col span={8}>
                                            <Form.Item label="性别" name="gender">
                                                <Select
                                                    style={{width: 200}}
                                                >
                                                    <Option value="1">男</Option>
                                                    <Option value="2">女</Option>
                                                </Select>
                                            </Form.Item>
                                        </Col>
                                        <Col span={8}>
                                            <Form.Item label="状态" name="status">
                                                <Select
                                                    style={{width: 200}}
                                                >
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
                                                <Button onClick={() => {
                                                    form.resetFields()
                                                }}>
                                                    重置
                                                </Button>
                                            </Space>
                                        </Col>
                                    </Row>
                                </Form>
                            </> : null}
                    </Card>
                    <Card size="small" bordered={false} className="card">
                        <div>
                            <Space style={{float: 'right'}}>
                                <Button key="add" type="primary" icon={<PlusOutlined/>}>新增</Button>
                                <Button key="export" icon={<ExportOutlined/>}>导出</Button>
                            </Space>
                            <Space style={{marginBottom: 6}}>
                            <Alert message="已选择 1 项" type="info" style={{width: 300, height: 32}}
                                   action={
                                       <Button size="small" type="link">
                                           取消选择
                                       </Button>
                                   }/>
                                <Button key="batchDelete" icon={<MinusOutlined/>}>批量删除</Button>
                            </Space>
                        </div>
                        <Table
                            columns={[
                                {
                                    title: '编号',
                                    dataIndex: 'userNo',
                                    key: 'userNo',
                                },
                                {
                                    title: '用户名',
                                    dataIndex: 'username',
                                    key: 'username',
                                    render: value => <a>{value}</a>,
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
                                    render: value => value === 1 ? '男' : '女',
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
                                    render: value => <Tag color={value === 1 ? "green" : "red"}>
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
                                    render: (text, record) => (
                                        <Space>
                                            <a>编辑</a>
                                            <a>删除</a>
                                        </Space>
                                    ),
                                },
                            ]}
                            dataSource={[
                                {
                                    key: '1',
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
                                    key: '2',
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
                                    key: '3',
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
                                    key: '4',
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
                                    key: '5',
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
                                    key: '6',
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
                                    key: '7',
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
                                    key: '8',
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
                                    key: '9',
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
                                    key: '10',
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
                                    key: '11',
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
                                    key: '12',
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
                            ]}
                            rowSelection={{
                                type: "checkbox",
                                onChange: (selectedRowKeys: React.Key[], selectedRows: any[]) => {
                                    console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
                                }
                            }}
                        />
                    </Card>
                </Content>
            </Layout>
        </Layout>
    );
}

export default App;