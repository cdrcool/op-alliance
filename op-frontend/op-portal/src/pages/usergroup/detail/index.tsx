import {Button, Card, Collapse, Descriptions, Popconfirm, Space, Spin, Table} from 'antd';
import {useHistory, useParams} from "react-router-dom";
import React, {FC, useEffect, useState} from "react";
import {PageContainer} from "@ant-design/pro-layout";
import {deleteUserGroup, getUserGroup} from "../../../services/userGroup";
import {UserGroup} from "../../../models/UserGroup";
import {User} from "../../../models/User";

const {Panel} = Collapse;

const UserGroupDetailPage: FC = () => {
    const history = useHistory();
    const {id} = useParams<{ id: string }>();

    const [loading, setLoading] = useState<boolean>(true);
    const [userGroup, setUserGroup] = useState<UserGroup>({});

    useEffect(() => {
        const fetchData = async () => {
            const userGroup = await getUserGroup(parseInt(id));
            setUserGroup(userGroup || {});
            setLoading(false);
        }

        fetchData().then(() => {
        });
    }, []);

    const onDeleteUserGroups = (id: string) => {
        deleteUserGroup(parseInt(id)).then(() => history.push('/admin/userGroup'));
    }

    return (
        <PageContainer
            className="page-container"
            title="用户组详情"
            header={{
                breadcrumb: {},
            }}
            extra={
                <Space>
                    <Button type="primary" onClick={() => history.push(`/admin/userGroup/edit/${id}`)}>编辑</Button>
                    <Popconfirm
                        title="确定要删除吗？"
                        okText="确定"
                        cancelText="取消"
                        onConfirm={() => onDeleteUserGroups(id)}
                    >
                        <Button>删除</Button>
                    </Popconfirm>
                    <Button>打印</Button>
                </Space>
            }
            onBack={() => history.push('/admin/userGroup')}
        >
            <Spin spinning={loading}>
                <Card>
                    <Collapse bordered={false} ghost={true} activeKey={['baseInfo', 'users']}>
                        <Panel header="基本信息" key="baseInfo" showArrow={false}>
                            <Descriptions>
                                <Descriptions.Item label="用户组名称">{userGroup.groupName}</Descriptions.Item>
                                <Descriptions.Item label="用户组描述">{userGroup.groupDesc}</Descriptions.Item>
                                <Descriptions.Item label="用户组编号">{userGroup.groupNo}</Descriptions.Item>
                            </Descriptions>
                        </Panel>
                        <Panel header="用户列表" key="users" showArrow={false}>
                            <Table<User>
                                rowKey="id"
                                columns={[
                                    {title: '用户名', dataIndex: 'username'},
                                    {title: '昵称', dataIndex: 'nickname'},
                                    {title: '手机号', dataIndex: 'phone'},
                                    {title: '邮箱', dataIndex: 'email'},
                                    {
                                        title: '帐号状态',
                                        dataIndex: 'status',
                                        render: ((value) => {
                                            return value;
                                        })
                                    },
                                ]}
                                dataSource={userGroup.users || []}
                                pagination={false}
                            />
                        </Panel>
                    </Collapse>
                </Card>
            </Spin>
        </PageContainer>
    );
};

export default UserGroupDetailPage;