import React, {FC} from "react";
import {Alert, Breadcrumb, Button, Card, Input, Space, Table} from "antd";
import {
    ArrowLeftOutlined,
    ExportOutlined,
    MinusOutlined,
    PlusOutlined,
    SearchOutlined,
    UpOutlined
} from "@ant-design/icons";

const MenuPage: FC = () => {

    const columns = [
       /* {
            title: '编号',
            dataIndex: 'menuNo',
            key: 'menuNo',
        },*/
        {
            title: '菜单名称',
            dataIndex: 'menuName',
            key: 'menuName',
            render: (value: string) => <a>{value}</a>,
        },
        {
            title: '菜单编码',
            dataIndex: 'menuCode',
            key: 'menuCode',
        },
        {
            title: '菜单路由',
            dataIndex: 'menuRoute',
            key: 'menuRoute',
        },
        {
            title: '菜单图标',
            dataIndex: 'menuIcon',
            key: 'menuIcon',
        },
        {
            title: '是否目录',
            dataIndex: 'isDirectory',
            key: 'isDirectory',
            render: (value: number) => value === 1 ? '是' : '否',
        },
        {
            title: '是否隐藏',
            dataIndex: 'isHidden',
            key: 'isHidden',
            render: (value: number) => value === 1 ? '是' : '否',
        },
        {
            title: '权限标识',
            dataIndex: 'permission',
            key: 'permission',
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
            id: 1,
            menuNo: 1,
            menuName: '工作台',
            menuCode: 'workbench',
            menuIcon: null,
            menuRoute: '/workbench',
            isDirectory: 0,
            isHidden: 0,
            permission: 'workbench:view',
        },
        {
            id: 2,
            menuNo: 2,
            menuName: '管理中心',
            menuCode: 'management',
            menuIcon: null,
            menuRoute: '/management',
            isDirectory: 1,
            isHidden: 0,
            children: [
                {
                    id: 3,
                    pid: 2,
                    menuNo: 1,
                    menuName: '组织管理',
                    menuCode: 'organization',
                    menuIcon: null,
                    menuRoute: '/management/organization',
                    isDirectory: 0,
                    isHidden: 0,
                    permission: 'management:organization:view',
                },
                {
                    id: 4,
                    pid: 2,
                    menuNo: 2,
                    menuName: '用户管理',
                    menuCode: 'user',
                    menuIcon: null,
                    menuRoute: '/management/user',
                    isDirectory: 0,
                    isHidden: 0,
                    permission: 'management:user:view',
                },
                {
                    id: 5,
                    pid: 2,
                    menuNo: 3,
                    menuName: '角色',
                    menuCode: 'role',
                    menuIcon: null,
                    menuRoute: '/management/role',
                    isDirectory: 0,
                    isHidden: 0,
                    permission: 'management:role:view',
                },
                {
                    id: 6,
                    pid: 2,
                    menuNo: 4,
                    menuName: '资源管理',
                    menuCode: 'resource',
                    menuIcon: null,
                    menuRoute: '/management/resource',
                    isDirectory: 0,
                    isHidden: 0,
                    permission: 'management:resource:view',
                },
                {
                    id: 7,
                    pid: 2,
                    menuNo: 5,
                    menuName: '菜单管理',
                    menuCode: 'menu',
                    menuIcon: null,
                    menuRoute: '/management/menu',
                    isDirectory: 0,
                    isHidden: 0,
                    permission: 'management:menu:view',
                },
            ],
        },
        {
            id: 3,
            menuNo: 3,
            menuName: '统计中心',
            menuCode: 'statistics',
            menuIcon: null,
            menuRoute: '/statistics',
            isDirectory: 0,
            isHidden: 1,
            permission: 'statistics:view',
        },
        {
            id: 4,
            menuNo: 4,
            menuName: '日志中心',
            menuCode: 'logging',
            menuIcon: null,
            menuRoute: '/logging',
            isDirectory: 0,
            isHidden: 1,
            permission: 'logging:view',
        },
        {
            id: 5,
            menuNo: 5,
            menuName: '监控中心',
            menuCode: 'monitor',
            menuIcon: null,
            menuRoute: '/monitor',
            isDirectory: 0,
            isHidden: 1,
            permission: 'monitor:view',
        },
        {
            id: 6,
            menuNo: 6,
            menuName: '附件中心',
            menuCode: 'attachment',
            menuIcon: null,
            menuRoute: '/attachment',
            isDirectory: 0,
            isHidden: 1,
            permission: 'attachment:view',
        },
        {
            id: 7,
            menuNo: 7,
            menuName: '文档中心',
            menuCode: 'document',
            menuIcon: null,
            menuRoute: '/document',
            isDirectory: 0,
            isHidden: 1,
            permission: 'document:view',
        },
    ];

    return (
        <>
            <Card size="small" className="card">
                <Space className="breadcrumb">
                    <ArrowLeftOutlined/>
                    <Breadcrumb>
                        <Breadcrumb.Item>管理中心</Breadcrumb.Item>
                        <Breadcrumb.Item>菜单管理</Breadcrumb.Item>
                    </Breadcrumb>
                </Space>

                <div style={{float: 'right', marginBottom: 4}}>
                    <Space>
                        <Input placeholder="输入菜单名称、菜单编码或菜单路由查询" suffix={<SearchOutlined/>} allowClear={true} style={{width: 400}}/>
                    </Space>
                </div>
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

                <Table
                    columns={columns}
                    dataSource={dataSource}
                    pagination={false}
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

export default MenuPage;