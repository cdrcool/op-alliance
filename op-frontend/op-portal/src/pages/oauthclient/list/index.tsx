import React, {FC, useRef, useState} from "react";
import {useHistory} from "react-router-dom";
import {Button, Popconfirm, Space} from "antd";
import {ExportOutlined, MinusOutlined, PlusOutlined} from "@ant-design/icons";
import {PageContainer} from "@ant-design/pro-layout";
import type {ActionType, ProColumns} from '@ant-design/pro-table';
import ProTable, {TableDropdown} from '@ant-design/pro-table';
import {ModalForm, ProFormText,} from '@ant-design/pro-form';
import {Role} from "../../../models/Role";
import {deleteOauthClients, queryOauthClientPage} from "../../../services/oauthclient";
import {OauthClient} from "../../../models/OauthClient";

const OauthClientListPage: FC = () => {
    const history = useHistory();
    const ref = useRef<ActionType>();

    const [modalVisit, setModalVisit] = useState<boolean>(false);

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
            title: '授权许可类型',
            dataIndex: 'authorizedGrantTypes',
            renderText: (value: string) => {
                return value && value.split(',')
                    .map(item => item === 'authorization_code' ? '授权码模式' :
                        (item === 'password' ? '密码模式' :
                                (item === 'implicit' ? '隐式模式' :
                                        (item === 'client_credentials' ? '客户端模式' :
                                                (item === 'refresh_token' ? '刷新token' : '')
                                        )
                                )
                        ))
                    .join(',')
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
                <TableDropdown
                    key="actions"
                    menus={[
                        {
                            key: 'resetClientSecret',
                            name: '重置客户端秘钥',
                            onClick: () => setModalVisit(true),
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
            <ModalForm
                title="重置客户端秘钥"
                visible={modalVisit}
                onVisibleChange={setModalVisit}
                onFinish={async () => {
                    return true;
                }}
            >
                <ProFormText.Password name="clientSecret" label="客户端秘钥" placeholder="请输入客户端秘钥" rules={[{required: true, message: '请输入客户端秘钥'}]}/>
                <ProFormText.Password name="confirmClientSecret" label="客户端秘钥确认" placeholder="请再次输入客户端秘钥" rules={[{required: true, message: '请再次输入客户端秘钥'}]}/>
            </ModalForm>
        </PageContainer>
    )
};

export default OauthClientListPage;
