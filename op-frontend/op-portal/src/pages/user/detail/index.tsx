import {Button, Descriptions, PageHeader, Popconfirm, Space, Spin} from 'antd';
import {useHistory, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {User} from "../../../models/User";
import {deleteUser, getUser} from "../../../services/user";

const UserDetailPage = () => {
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

    const handleDelete = (id: string) => {
        deleteUser(parseInt(id)).then(() => history.push('/management/user'));
    }

    return (
        <div className="card">
            <PageHeader
                title={<span className="title">用户详情</span>}
                extra={
                    <Space>
                        <Button type="primary" onClick={() => history.push(`/management/user-edit/${id}`)}>编辑</Button>
                        <Popconfirm
                            title="确定要删除吗？"
                            okText="确定"
                            cancelText="取消"
                            onConfirm={() => handleDelete(id)}
                        >
                            <Button>删除</Button>
                        </Popconfirm>
                        <Button>打印</Button>
                    </Space>
                }
                onBack={() => history.push('/management/user')}
            >
                <Spin spinning={loading}>
                    <Descriptions>
                        <Descriptions.Item label="用户名">{user.nickname}</Descriptions.Item>
                        <Descriptions.Item label="昵称">{user.nickname}</Descriptions.Item>
                        <Descriptions.Item label="手机号">{user.phone}</Descriptions.Item>
                        <Descriptions.Item label="邮箱">{user.email}</Descriptions.Item>
                        <Descriptions.Item label="出生日期">{user.birthday}</Descriptions.Item>
                        <Descriptions.Item
                            label="是否启用">{user.status && (user.status === 1 ? '启用' : (user.status === 2 ? '禁用' : (user.status === 3 ? '过期' : '密码过期')))}</Descriptions.Item>
                    </Descriptions>
                </Spin>
            </PageHeader>
        </div>
    );
};

export default UserDetailPage;