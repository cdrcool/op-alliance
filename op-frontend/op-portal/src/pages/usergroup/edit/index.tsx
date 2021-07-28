import {Button, Card, Collapse, Form, Input, InputNumber, Space, Spin} from 'antd';
import {useHistory, useParams} from "react-router-dom";
import React, {FC, useEffect, useState} from "react";
import {PageContainer} from "@ant-design/pro-layout";
import {User} from "../../../models/User";
import {getUserGroup, saveUserGroup} from "../../../services/userGroup";

const {Panel} = Collapse;
const {TextArea} = Input;

const UserGroupEditPage: FC = () => {
    const history = useHistory();
    const {id} = useParams<{ id?: string }>();

    const [loading, setLoading] = useState<boolean>(!!id);
    const [groupUsers, setGroupUsers] = useState<User[]>([]);
    const [form] = Form.useForm();

    useEffect(() => {
        if (id) {
            const fetchData = async () => {
                const userGroup = await getUserGroup(parseInt(id));
                const {users, ...fieldValues} = userGroup;
                form.setFieldsValue(fieldValues);
                setGroupUsers(users || []);
                setLoading(false);
            }

            fetchData().then(() => {
            });
        }
    }, []);

    const onSave = () => {
        form.validateFields().then(values => {
            saveUserGroup(values).then(() => {
                history.push('/admin/userGroup');
            });
        });
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
                    <Collapse bordered={false} ghost={true} activeKey={['baseInfo', 'actions']}>
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

                        </Panel>
                    </Collapse>
                </Card>
            </Spin>
        </PageContainer>
    );
};

export default UserGroupEditPage;