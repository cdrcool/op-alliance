import React, {FC, useRef} from "react";
import {useHistory} from "react-router-dom";
import {Button, Popconfirm} from "antd";
import {ExportOutlined, PlusOutlined} from "@ant-design/icons";
import {PageContainer} from "@ant-design/pro-layout";
import type {ActionType, ProColumns} from '@ant-design/pro-table';
import ProTable, {TableDropdown} from '@ant-design/pro-table';
import {Role} from "../../../models/Role";
import {Job} from "../../../models/Job";
import {deleteJobs, pauseJob, queryJobPage, resumeJob, triggerJob} from "../../../services/job";

const JobListPage: FC = () => {
    const history = useHistory();
    const ref = useRef<ActionType>();

    const onDeleteJobs = (jobIds: string[]) => {
        // @ts-ignore
        deleteJobs(jobIds).then(() => ref.current.reloadAndRest());
    };

    const onChangeJobStatus = (jobId: string, enable: boolean) => {
        if (enable) {
            // @ts-ignore
            resumeJob(jobId, enable).then(() => ref.current.reload());
        } else {
            // @ts-ignore
            pauseJob(jobId, enable).then(() => ref.current.reload());
        }
    };

    const onTriggerJob = (jobId: string) => {
        // @ts-ignore
        triggerJob(jobId).then();
    };

    const columns: ProColumns<Job>[] = [
        {
            title: '任务标识',
            dataIndex: 'jobId',
        },
        {
            title: '任务名称',
            dataIndex: 'jobName',
        },
        {
            title: '任务执行类',
            dataIndex: 'jobClass',
        },
        {
            title: 'cron表达式',
            dataIndex: 'cronExps',
        },
        {
            title: '运行状态',
            dataIndex: 'status',
            valueEnum: {
                0: {text: '已暂停', status: 'Error'},
                1: {text: '运行中', status: 'Success'},
            },
            filters: true,
            onFilter: true,
        },
        {
            title: '操作',
            valueType: 'option',
            render: (text, record, _, action) => [
                <a key="edit" onClick={() => history.push(`/config/job/edit/${record.id}`)}>
                    编辑
                </a>,
                <Popconfirm
                    title="确定要删除吗？"
                    okText="确定"
                    cancelText="取消"
                    onConfirm={() => onDeleteJobs([record.jobId] as string[])}
                >
                    <a key="delete">
                        删除
                    </a>
                </Popconfirm>,
                <a key="view" onClick={() => history.push(`/config/job/detail/${record.id}`)}>
                    查看
                </a>,
                <TableDropdown
                    key="actions"
                    menus={[
                        {
                            key: 'enable',
                            name: record.status === 1 ? '暂停' : '恢复',
                            onClick: () => onChangeJobStatus(record.jobId as string, record.status === 0),
                        },
                        {
                            key: 'trigger',
                            name: '触发',
                            hidden: record.status === 0,
                            onClick: () => onTriggerJob(record.jobId as string),
                        },
                    ]}
                />,
            ],
        },
    ];

    return (
        <PageContainer className="page-container" header={{
            breadcrumb: {},
        }}>
            <ProTable<Role>
                actionRef={ref}
                rowKey="id"
                search={false}
                options={{
                    search: {
                        placeholder: "输入任务标识或任务名称查询",
                        style: {width: 400},
                    },
                    fullScreen: true,
                }}
                toolBarRender={() => [
                    <Button key="button" type="primary" icon={<PlusOutlined/>}
                            onClick={() => history.push('/config/job/edit')}>
                        新建
                    </Button>,
                    <Button key="button" icon={<ExportOutlined/>}>
                        导出
                    </Button>,
                ]}
                columns={columns}
                request={
                    async (params, sort, filter) => {
                        const {current, pageSize, keyword} = params;
                        const result = await queryJobPage(
                            (current || 1) - 1,
                            pageSize || 10,
                            {
                                keyword,
                                ...filter
                            }
                        );
                        return {
                            data: result.content,
                            success: true,
                            total: result.totalElements,
                        };
                    }}
                rowSelection={{}}
                pagination={{
                    pageSize: 10,
                }}
            />
        </PageContainer>
    )
};

export default JobListPage;
