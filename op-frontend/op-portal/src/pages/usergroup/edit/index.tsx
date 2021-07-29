import {Badge, Button, Card, Collapse, Form, Input, InputNumber, Space, Spin} from 'antd';
import {useHistory, useParams} from "react-router-dom";
import React, {FC, useEffect, useRef, useState} from "react";
import {PageContainer} from "@ant-design/pro-layout";
import ProTable, {ActionType} from '@ant-design/pro-table';
import {User} from "../../../models/User";
import {getUserGroup, saveUserGroup} from "../../../services/userGroup";
import {ExportOutlined, MinusOutlined, PlusOutlined} from "@ant-design/icons";
import UserReferPage from "../../user/refer";

const {Panel} = Collapse;
const {TextArea} = Input;

const UserGroupEditPage: FC = () => {
    const history = useHistory();
    const {id} = useParams<{ id?: string }>();
    const ref = useRef<ActionType>();

    const [loading, setLoading] = useState<boolean>(!!id);
    const [groupUsers, setGroupUsers] = useState<User[]>([]);
    const [groupUserIds, setGroupUserIds] = useState<number[]>([]);
    const [userModelVisible, setUserModelVisible] = useState<boolean>(false);
    const [form] = Form.useForm();

    useEffect(() => {
        if (id) {
            const fetchData = async () => {
                const userGroup = await getUserGroup(parseInt(id));
                const {users, ...fieldValues} = userGroup;
                form.setFieldsValue(fieldValues);
                setGroupUsers(users || []);
                setGroupUserIds((users || []).map(user => user.id as number));
                setLoading(false);
            }

            fetchData().then(() => {
            });
        }
    }, []);

    const onSave = () => {
        form.validateFields().then(values => {
            saveUserGroup({
                ...values,
                userIds: groupUsers.map(item => item.id),
            }).then(() => {
                history.push('/admin/userGroup');
            });
        });
    };

    const onDeleteGroupUsers = (userIds: number[]) => {
        setGroupUsers(groupUsers.filter((item) => userIds.indexOf(item.id as number) === -1));
        setGroupUserIds(groupUserIds.filter((item) => userIds.indexOf(item) === -1));
        // @ts-ignore
        ref.current?.clearSelected();
    };

    const onHandleOk = (users: User[]) => {
        setGroupUsers([...groupUsers, ...users.filter(user => groupUserIds.indexOf(user.id as number) === -1)]);
        setGroupUserIds([...groupUserIds, ...users.filter(user => groupUserIds.indexOf(user.id as number) === -1).map(user => user.id as number)]);
        setUserModelVisible(false);
    };

    const onHandleCancel = () => {
        setUserModelVisible(false);
    };

    return (
        <PageContainer
            className="page-container"
            title={`${id ? '编辑' : '新增'}用户组`}
            header={{
                breadcrumb: {},
            }}
            extra={
                <Space>
                    <Button type="primary" onClick={onSave}>保存</Button>
                </Space>
            }
            onBack={() => history.push('/admin/userGroup')}
        >
            <Spin spinning={loading}>
                <Card>
                    <Collapse bordered={false} ghost={true} activeKey={['baseInfo', 'users']}>
                        <Panel header="基本信息" key="baseInfo" showArrow={false}>
                            <Form
                                form={form}
                                labelCol={{span: 8}}
                                wrapperCol={{span: 8}}
                            >
                                <Form.Item name="id" hidden={true}/>
                                <Form.Item label="用户组名称" name="groupName"
                                           rules={[{required: true, message: '请输入用户组名称'}]}>
                                    <Input/>
                                </Form.Item>
                                <Form.Item label="用户组描述" name="groupDesc">
                                    <TextArea/>
                                </Form.Item>
                                <Form.Item label="用户组编号" name="groupNo">
                                    <InputNumber/>
                                </Form.Item>
                            </Form>
                        </Panel>
                        <Panel header="用户列表" key="users" showArrow={false}>
                            <ProTable<User>
                                actionRef={ref}
                                rowKey="id"
                                search={false}
                                options={{
                                    reload: false,
                                    fullScreen: true,
                                }}
                                toolBarRender={() => [
                                    <Button key="button" type="primary" icon={<PlusOutlined/>}
                                            onClick={() =>setUserModelVisible(true)}>
                                        选择用户
                                    </Button>,
                                    <Button key="button" icon={<ExportOutlined/>}>
                                        导出
                                    </Button>,
                                ]}
                                tableAlertOptionRender={({selectedRowKeys}) => {
                                    return (
                                        <Space>
                                            <Button
                                                key="batchDelete"
                                                icon={<MinusOutlined/>}
                                                onClick={
                                                    () => onDeleteGroupUsers(selectedRowKeys as number[])
                                                }
                                            >
                                                批量删除
                                            </Button>
                                        </Space>
                                    );
                                }}
                                columns={[
                                    {title: '用户名', dataIndex: 'username'},
                                    {title: '昵称', dataIndex: 'nickname'},
                                    {title: '手机号', dataIndex: 'phone'},
                                    {title: '邮箱', dataIndex: 'email'},
                                    {
                                        title: '帐号状态',
                                        dataIndex: 'status',
                                        render: ((value) => {
                                            switch (value) {
                                                case 0:
                                                    return <Badge status="error" text="禁用"/>
                                                case 1:
                                                    return <Badge status="success" text="启用"/>
                                                case 2:
                                                    return <Badge status="error" text="过期"/>
                                                case 3:
                                                    return <Badge status="error" text="锁定"/>
                                                case 4:
                                                    return <Badge status="error" text="密码过期"/>
                                                default:
                                                    return null;
                                            }
                                        })
                                    },
                                ]}
                                dataSource={groupUsers || []}
                                rowSelection={{}}
                                pagination={false}
                            />
                            <UserReferPage
                                visible={userModelVisible}
                                defaultSelectedUsers={groupUsers}
                                defaultSelectedUserIds={groupUserIds}
                                handleOk={onHandleOk}
                                handleCancel={onHandleCancel}
                            />
                        </Panel>
                    </Collapse>
                </Card>
            </Spin>
        </PageContainer>
    );
};

export default UserGroupEditPage;