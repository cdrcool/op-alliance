import React, {FC, ReactText, useState} from 'react';
import {PageContainer} from "@ant-design/pro-layout";
import {useHistory, useParams} from "react-router-dom";
import ProList from '@ant-design/pro-list';
import ProCard from '@ant-design/pro-card';
import {Button, Pagination, Space} from "antd";

import "./index.css";

const UserAssignRolesPage: FC = () => {
    const history = useHistory();
    const {id} = useParams<{ id: string }>();
    const [selectedRowKeys, setSelectedRowKeys] = useState<ReactText[]>([]);

    return (
        <PageContainer
            className="page-container"
            title="用户角色分配"
            header={{
                breadcrumb: {},
            }}
            extra={
                <Space>
                    <Button type="primary">保存</Button>
                </Space>
            }
            onBack={() => history.push('/admin/user')}
        >
            <ProCard split="vertical">
                <ProCard colSpan="50%">
                    <ProList<{ title: string, description: string | undefined | null }>
                        style={{backgroundColor: '#ffffff'}}
                        metas={{
                            title: {},
                            description: {},
                        }}
                        rowKey="title"
                        rowSelection={
                            {
                                selectedRowKeys,
                                onChange: (keys: ReactText[]) => setSelectedRowKeys(keys),
                            }
                        }
                        dataSource={
                            [
                                {
                                    title: '管理员',
                                    description: '具有系统所有操作权限',
                                },
                                {
                                    title: '游客',
                                    description: '具有系统查看权限',
                                },
                                {
                                    title: '管理员2',
                                    description: '具有系统所有操作权限',
                                },
                                {
                                    title: '游客2',
                                    description: '具有系统查看权限',
                                },
                                {
                                    title: '管理员3',
                                    description: '具有系统所有操作权限',
                                },
                                {
                                    title: '游客3',
                                    description: '具有系统查看权限',
                                },
                                {
                                    title: '管理员4',
                                    description: '具有系统所有操作权限',
                                },
                                {
                                    title: '游客4',
                                    description: '具有系统查看权限',
                                },
                                {
                                    title: '管理员5',
                                    description: null,
                                },
                                {
                                    title: '游客5',
                                    description: undefined,
                                },
                            ]
                        }
                    />
                </ProCard>
                <ProCard>
                    <ProList<{ title: string, description: string | undefined | null }>
                        style={{backgroundColor: '#ffffff'}}
                        metas={{
                            title: {},
                            description: {},
                        }}
                        rowKey="title"
                        rowSelection={
                            {
                                selectedRowKeys,
                                onChange: (keys: ReactText[]) => setSelectedRowKeys(keys),
                            }
                        }
                        dataSource={
                            [
                                {
                                    title: '管理员',
                                    description: '具有系统所有操作权限',
                                },
                                {
                                    title: '游客',
                                    description: '具有系统查看权限',
                                },
                                {
                                    title: '管理员2',
                                    description: '具有系统所有操作权限',
                                },
                                {
                                    title: '游客2',
                                    description: '具有系统查看权限',
                                },
                                {
                                    title: '管理员3',
                                    description: '具有系统所有操作权限',
                                },
                                {
                                    title: '游客3',
                                    description: '具有系统查看权限',
                                },
                                {
                                    title: '管理员4',
                                    description: '具有系统所有操作权限',
                                },
                                {
                                    title: '游客4',
                                    description: '具有系统查看权限',
                                },
                                {
                                    title: '管理员5',
                                    description: null,
                                },
                                {
                                    title: '游客5',
                                    description: undefined,
                                },
                            ]
                        }
                    />
                </ProCard>
            </ProCard>
            <Pagination/>
        </PageContainer>
    );
};

export default UserAssignRolesPage;