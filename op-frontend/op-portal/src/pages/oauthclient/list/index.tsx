import React, {FC, useRef, useState} from "react";
import {useHistory} from "react-router-dom";
import {Button, message, Popconfirm, Space} from "antd";
import {ExportOutlined, MinusOutlined, PlusOutlined} from "@ant-design/icons";
import {PageContainer} from "@ant-design/pro-layout";
import type {ActionType, ProColumns} from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import {ModalForm, ProFormText,} from '@ant-design/pro-form';
import {Role} from "../../../models/Role";
import {changeOauthClientSecret, deleteOauthClients, queryOauthClientPage} from "../../../services/oauthclient";
import {OauthClient} from "../../../models/OauthClient";
import Authority from "../../../components/Authority";
import AuthTableDropDown from "../../../components/AuthTableDropdown";

const OauthClientListPage: FC = () => {
    const history = useHistory();
    const ref = useRef<ActionType>();

    const [selectedId, setSelectedId] = useState<number | null>();
    const [modalVisible, setModalVisible] = useState<boolean>(false);

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
                <Authority value="oauth_client_save">
                    <a key="edit" onClick={() => history.push(`/admin/oauthClient/edit/${record.id}`)}>
                        编辑
                    </a>
                </Authority>,
                <Authority value="oauth_client_delete">
                    <Popconfirm
                        title="确定要删除吗？"
                        okText="确定"
                        cancelText="取消"
                        onConfirm={() => onDeleteOauthClients([record.id] as number[])}
                    >
                        <a key="delete">
                            删除
                        </a>
                    </Popconfirm>
                </Authority>,
                <Authority value="oauth_client_view">
                    <a key="view" onClick={() => history.push(`/admin/oauthClient/detail/${record.id}`)}>
                        查看
                    </a>
                </Authority>,
                <AuthTableDropDown
                    key="actions"
                    menus={[
                        {
                            // @ts-ignore
                            authority: 'oauth_client_save',
                            key: 'resetClientSecret',
                            name: '重置客户端秘钥',
                            onClick: () => {
                                setSelectedId(record.id as number)
                                setModalVisible(true);
                            },
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
                    <Authority value="oauth_client_save">
                        <Button key="button" type="primary" icon={<PlusOutlined/>}
                                onClick={() => history.push('/admin/oauthClient/edit')}>
                            新建
                        </Button>
                    </Authority>,
                    <Authority value="oauth_client_export">
                        <Button key="button" icon={<ExportOutlined/>}>
                            导出
                        </Button>
                    </Authority>,
                ]}
                tableAlertOptionRender={({selectedRowKeys}) => {
                    return (
                        <Space>
                            <Authority value="oauth_client_delete">
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
                            </Authority>
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
                visible={modalVisible}
                onVisibleChange={(visible => {
                    setModalVisible(visible);
                    if (!visible) {
                        setSelectedId(null);
                    }
                })}
                onFinish={async (values) => {
                    await changeOauthClientSecret({id: selectedId, ...values});
                    message.success("重置秘钥成功！");
                    return true;
                }}
            >
                <ProFormText.Password name="secret" label="客户端秘钥" placeholder="请输入客户端秘钥"
                                      rules={[{required: true, message: '请输入客户端秘钥'}]}/>
                <ProFormText.Password name="confirmSecret" label="客户端秘钥确认" placeholder="请再次输入客户端秘钥"
                                      rules={[{required: true, message: '请再次输入客户端秘钥'}]}/>
            </ModalForm>
        </PageContainer>
    )
};

export default OauthClientListPage;
