import React, {FC} from "react";
import {Alert, Breadcrumb, Button, Card, Input, Space, Table} from "antd";
import {ArrowLeftOutlined, ExportOutlined, MinusOutlined, PlusOutlined, SearchOutlined} from "@ant-design/icons";

import './index.css';

const ResourcePage: FC = () => {
    const columns = [
        /*{
            title: '编号',
            dataIndex: 'resourceNo',
            key: 'resourceNo',
        },*/
        {
            title: '资源名称',
            dataIndex: 'resourceName',
            key: 'resourceName',
            render: (value: string) => <a>{value}</a>,
        },
        {
            title: '资源路径',
            dataIndex: 'resourcePath',
            key: 'resourcePath',
        },
        {
            title: '资源描述',
            dataIndex: 'resourceDesc',
            key: 'resourceDesc',
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
            id: 3,
            resourceNo: 1,
            resourceName: '组织管理',
            resourcePath: '/management/organization',
            resourceDesc: null,
        },
        {
            id: 4,
            resourceNo: 2,
            resourceName: '用户管理',
            resourcePath: '/management/user',
            resourceDesc: null,
        },
        {
            id: 5,
            resourceNo: 3,
            resourceName: '角色',
            resourcePath: '/management/role',
            resourceDesc: null,
        },
        {
            id: 6,
            resourceNo: 4,
            resourceName: '资源管理',
            resourcePath: '/management/resource',
            resourceDesc: null,
        },
        {
            id: 7,
            resourceNo: 5,
            resourceName: '菜单管理',
            resourcePath: '/management/menu',
            resourceDesc: null,
        },
    ];

    const expandedRowRender = (record: { id: number, resourceNo: number, resourceName: string, resourcePath: string, resourceDesc: string|null }) => {
        const columns = [
            {title: '动作名称', dataIndex: 'actionName', key: 'actionName'},
            {title: '动作路径', dataIndex: 'actionPath', key: 'actionPath'},
            {title: '动作描述', dataIndex: 'actionDesc', key: 'actionDesc'},
            {title: '权限名', dataIndex: 'permission', key: 'permission'},
            {
                title: '草错',
                dataIndex: 'operation',
                key: 'operation',
                render: () => (
                    <Space>
                        <a>编辑</a>
                        <a>删除</a>
                    </Space>
                ),
            },
        ];

        const data = [
            {
                id: record.id + "-" + 1,
                actionName: '查看',
                actionPath: 'view',
                actionDesc: null,
                permission: 'view',
            },
            {
                id: record.id + "-" + 2,
                actionName: '保存',
                actionPath: 'save',
                actionDesc: null,
                permission: 'save',
            },
            {
                id: record.id + "-" + 3,
                actionName: '删除',
                actionPath: 'delete',
                actionDesc: null,
                permission: 'delete',
            },
            {
                id: record.id + "-" + 4,
                actionName: '导出',
                actionPath: 'export',
                actionDesc: null,
                permission: 'export',
            },
        ];
        return <Table columns={columns} dataSource={data} rowKey="id"/>;
    };

    return (
        <>
            <Card size="small" className="card">
                <Space className="breadcrumb">
                    <ArrowLeftOutlined/>
                    <Breadcrumb>
                        <Breadcrumb.Item>管理中心</Breadcrumb.Item>
                        <Breadcrumb.Item>资源管理</Breadcrumb.Item>
                    </Breadcrumb>
                </Space>

                <div style={{float: 'right', marginBottom: 4}}>
                    <Space>
                        <Input placeholder="输入资源名称或资源路径查询" suffix={<SearchOutlined/>} allowClear={true}
                               style={{width: 400}}/>
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

                <Table columns={columns} dataSource={dataSource}
                       rowKey="id"
                       rowSelection={{
                           type: "checkbox",
                           onChange: (selectedRowKeys: React.Key[], selectedRows: any[]) => {
                               console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
                           }
                       }}
                       expandable={{expandedRowRender}}
                />
            </Card>
        </>
    )
};

export default ResourcePage;