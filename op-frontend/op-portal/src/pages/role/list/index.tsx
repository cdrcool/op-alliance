import React, {FC, useEffect, useState} from "react";
import {useHistory} from "react-router-dom";
import {Alert, Button, Card, Input, Popconfirm, Space, Table, Tag} from "antd";
import {ExportOutlined, MinusOutlined, PlusOutlined, SearchOutlined} from "@ant-design/icons";
import {Role} from "../../../models/Role";
import {ColumnsType} from "antd/es/table";
import {queryRolePage} from "../../../services/role";
import defaultPage, {Page} from "../../../models/Page";

const {Search} = Input;

const RoleListPage: FC = () => {
    const history = useHistory();

    const [loading, setLoading] = useState<boolean>(true);
    const [searchText, setSearchText] = useState<string>('');
    const [pageInfo, setPageInfo] = useState<Page<Role>>(defaultPage);
    const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([]);

    const fetchData = async () => {
        const result = await queryRolePage(pageInfo.number, pageInfo.size, searchText);
        setPageInfo(result || defaultPage);
        setLoading(false);
    }

    useEffect(() => {
        setLoading(true);
        fetchData().then(() => {
        });
    }, [pageInfo.number, pageInfo.size, searchText]);


    const deleteRoles = (ids: React.Key[]) => {
        console.log('ids', ids);
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
                        onConfirm={() => deleteRoles([record.id] as React.Key[])}
                    >
                        <Button type="link">删除</Button>
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
                            placeholder="输入角色名、角色编码或角色描述查询"
                            onSearch={(value) => setSearchText(value)}
                            style={{width: 400}}
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
                                        deleteRoles(selectedRowKeys);
                                        setSelectedRowKeys([]);
                                    }}
                                >
                                    <Button key="batchDelete" icon={<MinusOutlined/>}>
                                        批量删除
                                    </Button>
                                </Popconfirm>
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
                               total: pageInfo.totalElements,
                               pageSize: pageInfo.size,
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