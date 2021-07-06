import React, {FC, useEffect, useState} from "react";
import {useHistory} from "react-router-dom";
import {Alert, Button, Card, Dropdown, Input, Menu, Popconfirm, Space, Table, Tag} from "antd";
import {ExportOutlined, MinusOutlined, PlusOutlined} from "@ant-design/icons";
import {Role} from "../../../models/Role";
import {ColumnsType} from "antd/es/table";
import {queryRolePage, deleteRoles, changeRolesEnabled} from "../../../services/role";
import defaultPage, {Page} from "../../../models/Page";

const {Search} = Input;

const RoleListPage: FC = () => {
    const history = useHistory();

    const [loading, setLoading] = useState<boolean>(true);
    const [pageInfo, setPageInfo] = useState<Page<Role>>(defaultPage);
    const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([]);

    const fetchData = async (page: number, size: number, searchText?: string) => {
        setLoading(true);
        const result = await queryRolePage(page, size, searchText);
        setPageInfo(result || defaultPage);
        setLoading(false);
    }

    useEffect(() => {
        fetchData(pageInfo.number, pageInfo.size).then(() => {
        });
    }, [pageInfo.number, pageInfo.size]);

    const onDeleteRoles = (ids: number[]) => {
        deleteRoles(ids).then(() => fetchData(defaultPage.number, defaultPage.size));
    }

    const onChangeRolesEnabled = (ids: number[], enable: boolean) => {
        changeRolesEnabled(ids, enable).then(() => fetchData(defaultPage.number, defaultPage.size));
    }

    const columns: ColumnsType<Role> = [
        {
            title: '角色名',
            dataIndex: 'roleName',
            key: 'roleName',
            render: (value: string, record: Role) => <a
                onClick={() => history.push(`/management/role-detail/${record.id}`)}>{value}</a>,
        },
        {
            title: '角色编码',
            dataIndex: 'roleCode',
            key: 'roleCode',
        },
        {
            title: '启用状态',
            dataIndex: 'status',
            key: 'status',
            render: (value: number) => <Tag color={value === 1 ? "green" : "red"}>
                {value === 0 ? '禁用' : '启用'}
            </Tag>,
        },
        {
            title: '操作',
            key: 'action',
            render: (text: string, record: Role) => (
                <Space>
                    <Button type="link" onClick={() => history.push(`/management/role-edit/${record.id}`)}>编辑</Button>
                    <Popconfirm
                        title="确定要删除吗？"
                        okText="确定"
                        cancelText="取消"
                        onConfirm={() => onDeleteRoles([record.id] as number[])}
                    >
                        <Button type="link">删除</Button>
                        <Button type="link"
                                onClick={() => onChangeRolesEnabled([record.id] as number[], record.status === 0)}>
                            {record.status === 1 ? '禁用' : '启用'}
                        </Button>
                    </Popconfirm>
                </Space>
            ),
        },
    ];

    return (
        <>
            <Card size="small" className="card">
                <div style={{float: 'right', marginBottom: 4}}>
                    <Space>
                        <Search
                            style={{width: 400}}
                            placeholder="输入角色名、角色编码或角色描述查询"
                            allowClear
                            enterButton
                            onSearch={(value) => fetchData(defaultPage.number, defaultPage.size, value)}
                        />
                    </Space>
                </div>
            </Card>

            <Card className="card">
                <div>
                    <Space style={{float: 'right'}}>
                        <Button type="primary" icon={<PlusOutlined/>}
                                onClick={() => history.push('/management/role-edit')}>
                            新增
                        </Button>
                        <Button icon={<ExportOutlined/>}>
                            导出
                        </Button>
                    </Space>

                    {
                        selectedRowKeys.length > 0 ?
                            <Space>
                                <Alert message={`已选择 ${selectedRowKeys.length} 项`} type="info"
                                       style={{width: 400, height: 32}}
                                       action={<Button size="small" type="link"
                                                       onClick={() => setSelectedRowKeys([])}>
                                           取消选择
                                       </Button>}/>
                                <Popconfirm
                                    title="确定要删除吗？"
                                    okText="确定"
                                    cancelText="取消"
                                    onConfirm={() => {
                                        onDeleteRoles(selectedRowKeys as number[]);
                                        setSelectedRowKeys([]);
                                    }}
                                >
                                    <Button key="batchDelete" icon={<MinusOutlined/>}>
                                        批量删除
                                    </Button>
                                </Popconfirm>
                                <Dropdown.Button overlay={
                                    <Menu onClick={(menuInfo) => onChangeRolesEnabled(selectedRowKeys as number[], parseInt(menuInfo.key) === 1)}>
                                        <Menu.Item key={1}>启用</Menu.Item>
                                        <Menu.Item key={0}>禁用</Menu.Item>
                                    </Menu>
                                }
                                >
                                    启用|禁用
                                </Dropdown.Button>
                            </Space> : null
                    }
                </div>

                <Table rowKey="id" loading={loading} columns={columns} dataSource={pageInfo.content}
                       rowSelection={{
                           type: "checkbox",
                           selectedRowKeys: selectedRowKeys,
                           onChange: (selectedRowKeys: React.Key[], selectedRows: Role[]) => {
                               setSelectedRowKeys(selectedRowKeys);
                           }
                       }}
                       pagination={
                           {
                               current: pageInfo.number + 1,
                               pageSize: pageInfo.size,
                               total: pageInfo.totalElements,
                               showTotal: total => `总共 ${total} 条数据`,
                               onChange: (page, pageSize) => {
                                   setPageInfo(pre => Object.assign({}, {...pre}, {number:page - 1, size: pageSize}));
                               }
                           }
                       }
                />
            </Card>
        </>
    )
};

export default RoleListPage;