import {Button, Card, Descriptions, Popconfirm, Space, Spin} from 'antd';
import {useHistory, useParams} from "react-router-dom";
import React, {FC, useEffect, useState} from "react";
import {PageContainer} from "@ant-design/pro-layout";
import {Job} from "../../../models/Job";
import {deleteJob, getJob} from "../../../services/job";

const JobDetailPage: FC = () => {
    const history = useHistory();
    const {id} = useParams<{ id: string }>();

    const [loading, setLoading] = useState<boolean>(true);
    const [job, setJob] = useState<Job>({});

    useEffect(() => {
        const fetchData = async () => {
            const job = await getJob(parseInt(id));
            setJob(job || {});
            setLoading(false);
        }

        fetchData().then(() => {
        });
    }, []);

    const onDeleteJob = (id: string) => {
        deleteJob(parseInt(id)).then(() => history.push('/config/job'));
    }

    return (
        <PageContainer
            className="page-container"
            title="定时任务"
            header={{
                breadcrumb: {},
            }}
            extra={
                <Space>
                    <Button type="primary" onClick={() => history.push(`/config/job/edit/${id}`)}>编辑</Button>
                    <Popconfirm
                        title="确定要删除吗？"
                        okText="确定"
                        cancelText="取消"
                        onConfirm={() => onDeleteJob(id)}
                    >
                        <Button>删除</Button>
                    </Popconfirm>
                    <Button>打印</Button>
                </Space>
            }
            onBack={() => history.push('/config/job')}
        >
            <Spin spinning={loading}>
                <Card>
                    <Descriptions>
                        <Descriptions.Item label="任务标识">{job.jobId}</Descriptions.Item>
                        <Descriptions.Item label="任务名称">{job.jobName}</Descriptions.Item>
                        <Descriptions.Item label="任务执行类">{job.jobClass}</Descriptions.Item>
                        <Descriptions.Item label="cron表达式">{job.cronExps}</Descriptions.Item>
                        <Descriptions.Item
                            label="运行状态">{job.status && (job.status === 1 ? '运行中' : '已暂停')}</Descriptions.Item>
                    </Descriptions>
                </Card>
            </Spin>
        </PageContainer>
    );
};

export default JobDetailPage;
