import React, {FC, useRef} from "react";
import {useHistory} from "react-router-dom";
import {Button, Popconfirm, Space} from "antd";
import {ExportOutlined, MinusOutlined, PlusOutlined} from "@ant-design/icons";
import {PageContainer} from "@ant-design/pro-layout";
import type {ActionType, ProColumns} from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import {Role} from "../../../models/Role";
import {deleteOauthClients, queryOauthClientPage} from "../../../services/oauthclient";
import {OauthClient} from "../../../models/OauthClient";

const OauthClientListPage: FC = () => {
    const history = useHistory();
    const ref = useRef<ActionType>();

    const onDeleteOauthClients = (ids: number[]) => {
        // @ts-ignore
        deleteOauthClients(ids).then(() => ref.current.reloadAndRest());
    };

    const columns: ProColumns<OauthClient>[] = [
        {
            title: '客户端标识',
            dataIndex: 'clientId',
        },
        {
            title: '客户端密钥',
            dataIndex: 'clientSecret',
        },
        {
            title: '授权许可类型',
            dataIndex: 'authorizedGrantTypes',
            valueEnum: {
                'authorization_code': {text: '授权码模式'},
                'password': {text: '密码模式'},
                'implicit': {text: '隐式模式'},
                'client_credentials': {text: '客户端模式'},
                'refresh_token': {text: '刷新token'},
            },
            filters: true,
            onFilter: true,
        },
        {
            title: '授权范围',
            dataIndex: 'scope',
        },
        {
            title: '重定向地址',
            dataIndex: 'webServerRedirectUri',
        },
        {
            title: '操作',
            valueType: 'option',
            render: (text, record, _, action) => [
                <a key="edit" onClick={() => history.push(`/admin/oauthClient/edit/${record.id}`)}>
                    编辑
                </a>,
                <Popconfirm
                    title="确定要删除吗？"
                    okText="确定"
                    cancelText="取消"
                    onConfirm={() => onDeleteOauthClients([record.id] as number[])}
                >
                    <a key="delete">
                        删除
                    </a>
                </Popconfirm>,
                <a key="view" onClick={() => history.push(`/admin/oauthClient/detail/${record.id}`)}>
                    查看
                </a>,
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
                        placeholder: "输入客户端标识查询",
                        style: {width: 400},
                    },
                    fullScreen: true,
                }}
                toolBarRender={() => [
                    <Button key="button" type="primary" icon={<PlusOutlined/>}
                            onClick={() => history.push('/admin/oauthClient/edit')}>
                        新建
                    </Button>,
                    <Button key="button" icon={<ExportOutlined/>}>
                        导出
                    </Button>,
                ]}
                tableAlertOptionRender={({selectedRowKeys}) => {
                    return (
                        <Space>
                            <Popconfirm
                                title="确定要删除吗？"
                                okText="确定"
                                cancelText="取消"
                                onConfirm={() => onDeleteOauthClients(selectedRowKeys as number[])}
                            >
                                <Button key="batchDelete" icon={<MinusOutlined/>}>
                                    批量删除
                                </Button>
                            </Popconfirm>
                        </Space>
                    );
                }}
                columns={columns}
                request={
                    async (params, sort, filter) => {
                        const {current, pageSize, keyword} = params;
                        const result = await queryOauthClientPage(
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

export default OauthClientListPage;
