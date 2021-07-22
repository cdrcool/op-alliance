import {Button, Card, Descriptions, Popconfirm, Space, Spin} from 'antd';
import {useHistory, useParams} from "react-router-dom";
import React, {FC, useEffect, useState} from "react";
import {User} from "../../../models/User";
import {deleteUser, getUser} from "../../../services/user";
import {PageContainer} from "@ant-design/pro-layout";

const UserDetailPage: FC = () => {
    const history = useHistory();
    const {id} = useParams<{ id: string }>();

    const [loading, setLoading] = useState<boolean>(true);
    const [user, setUser] = useState<User>({});

    useEffect(() => {
        const fetchData = async () => {
            const user = await getUser(parseInt(id));
            setUser(user || {});
            setLoading(false);
        }

        fetchData().then(() => {
        });
    }, []);

    const onDeleteUser = (id: string) => {
        deleteUser(parseInt(id)).then(() => history.push('/admin/user'));
    }

    return (
        <PageContainer
            className="page-container"
            title="用户详情"
            header={{
                breadcrumb: {},
            }}
            extra={
                <Space>
                    <Button type="primary" onClick={() => history.push(`/admin/user/edit/${id}`)}>编辑</Button>
                    <Popconfirm
                        title="确定要删除吗？"
                        okText="确定"
                        cancelText="取消"
                        onConfirm={() => onDeleteUser(id)}
                    >
                        <Button>删除</Button>
                    </Popconfirm>
                    <Button>打印</Button>
                </Space>
            }
            onBack={() => history.push('/admin/user')}

        >
            <Spin spinning={loading}>
                <Card>
                    <Descriptions>
                        <Descriptions.Item label="用户名">{user.nickname}</Descriptions.Item>
                        <Descriptions.Item label="昵称">{user.nickname}</Descriptions.Item>
                        <Descriptions.Item label="手机号">{user.phone}</Descriptions.Item>
                        <Descriptions.Item label="邮箱">{user.email}</Descriptions.Item>
                        <Descriptions.Item label="性别">{user.gender && user.gender === 1 ? '男' : '女'}</Descriptions.Item>
                        <Descriptions.Item label="出生日期">{user.birthday}</Descriptions.Item>
                        <Descriptions.Item label="个性签名">{user.signature}</Descriptions.Item>
                        <Descriptions.Item label="帐号状态">
                            {user.status && (user.status === 1 ? '启用' : (user.status === 2 ? '禁用' : (user.status === 3 ? '过期' : '密码过期')))}
                        </Descriptions.Item>
                    </Descriptions>
                </Card>
            </Spin>
        </PageContainer>
    );
};

export default UserDetailPage;