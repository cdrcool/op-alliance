import {Button, Card, Form, Input, Radio, Space, Spin} from 'antd';
import {useHistory, useParams} from "react-router-dom";
import React, {FC, useEffect, useState} from "react";
import {PageContainer} from "@ant-design/pro-layout";
import {getJob, saveJob} from "../../../services/job";

const {TextArea} = Input;

const JobEditPage: FC = () => {
    const history = useHistory();
    const {id} = useParams<{ id?: string }>();

    const [loading, setLoading] = useState<boolean>(!!id);
    const [form] = Form.useForm();

    useEffect(() => {
        if (id) {
            const fetchData = async () => {
                const job = await getJob(parseInt(id));
                form.setFieldsValue(job);
                setLoading(false);
            }

            fetchData().then(() => {
            });
        }
    }, []);

    const onSave = () => {
        form.validateFields().then(values => {
            saveJob(values).then(() => {
                history.push('/config/job');
            });
        })
    };

    return (
        <PageContainer
            className="page-container"
            title={`${id ? '编辑' : '新增'}定时任务`}
            header={{
                breadcrumb: {},
            }}
            extra={
                <Space>
                    <Button type="primary" onClick={onSave}>保存</Button>
                </Space>
            }
            onBack={() => history.push('/config/job')}
        >
            <Spin spinning={loading}>
                <Card>
                    <Form
                        form={form}
                        labelCol={{span: 8}}
                        wrapperCol={{span: 8}}
                    >
                        <Form.Item name="id" hidden={true}/>
                        <Form.Item label="任务标识" name="jobId" rules={[{required: true, message: '请输入任务标识'}]}>
                            <Input/>
                        </Form.Item>
                        <Form.Item label="任务名称" name="jobName" rules={[{required: true, message: '请输入任务名称'}]}>
                            <Input/>
                        </Form.Item>
                        <Form.Item label="任务执行类" name="jobClass" rules={[{required: true, message: '请输入任务执行类'}]}>
                            <Input/>
                        </Form.Item>
                        <Form.Item label="cron表达式" name="cronExps" rules={[{required: true, message: 'cron表达式'}]}>
                            <Input/>
                        </Form.Item>
                        <Form.Item label="运行状态" name="status" initialValue={1}
                                   rules={[{required: true, message: '请勾选运行状态'}]}>
                            <Radio.Group>
                                <Radio value={1}>运行</Radio>
                                <Radio value={0}>暂停</Radio>
                            </Radio.Group>
                        </Form.Item>
                    </Form>
                </Card>
            </Spin>
        </PageContainer>
    );
};

export default JobEditPage;
