import React, {FC, useRef} from "react";
import {useHistory} from "react-router-dom";
import {Button, Dropdown, Menu, message, Popconfirm, Space} from "antd";
import {ExportOutlined, MinusOutlined, PlusOutlined} from "@ant-design/icons";
import {PageContainer} from "@ant-design/pro-layout";
import type {ActionType, ProColumns} from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import {Role} from "../../../models/Role";
import {WhiteResource} from "../../../models/WhiteResource";
import {
    changeWhiteResourcesEnabled,
    deleteWhiteResources,
    initWhiteResourcePaths,
    queryWhiteResourcePage
} from "../../../services/whiteResource";
import Authority from "../../../components/Authority";
import AuthTableDropDown from "../../../components/AuthTableDropdown";

const WhiteResourceListPage: FC = () => {
    const history = useHistory();
    const ref = useRef<ActionType>();

    const onDeleteWhiteResources = (ids: number[]) => {
        // @ts-ignore
        deleteWhiteResources(ids).then(() => ref.current.reloadAndRest());
    };

    const onChangeWhiteResourcesEnabled = (ids: number[], enable: boolean) => {
        // @ts-ignore
        changeWhiteResourcesEnabled(ids, enable).then(() => ref.current.reload());
    };

    const columns: ProColumns<WhiteResource>[] = [
        {
            title: '资源名称',
            dataIndex: 'resourceName',
        },
        {
            title: '资源路径',
            dataIndex: 'resourcePath',
        },
        {
            title: '资源描述',
            dataIndex: 'resourceDesc',
        },
        {
            title: '是否移除身份认证',
            dataIndex: 'removeAuthorization',
            valueEnum: {
                true: {text: '是', status: 'Error'},
                false: {text: '否', status: 'Success'},
            },
            filters: true,
            onFilter: true,
        },
        {
            title: '启用状态',
            dataIndex: 'status',
            valueEnum: {
                0: {text: '禁用', status: 'Error'},
                1: {text: '启用', status: 'Success'},
            },
            filters: true,
            onFilter: true,
        },
        {
            title: '操作',
            valueType: 'option',
            render: (text, record, _, action) => [
                <Authority value="white_resource_save">
                    <a key="edit" onClick={() => history.push(`/admin/whiteResource/edit/${record.id}`)}>
                        编辑
                    </a>
                </Authority>,
                <Authority value="white_resource_delete">
                    <Popconfirm
                        title="确定要删除吗？"
                        okText="确定"
                        cancelText="取消"
                        onConfirm={() => onDeleteWhiteResources([record.id] as number[])}
                    >
                        <a key="delete">
                            删除
                        </a>
                    </Popconfirm>
                </Authority>,
                <Authority value="white_resource_view">
                    <a key="view" onClick={() => history.push(`/admin/whiteResource/detail/${record.id}`)}>
                        查看
                    </a>
                </Authority>,
                <AuthTableDropDown
                    key="actions"
                    menus={[
                        {
                            // @ts-ignore
                            authority: 'white_resource_change_enabled',
                            key: 'enable',
                            name: record.status === 1 ? '禁用' : '启用',
                            onClick: () => onChangeWhiteResourcesEnabled([record.id] as number[], record.status === 0),
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
                        placeholder: "输入资源名称、资源路径或资源描述查询",
                        style: {width: 400},
                    },
                    fullScreen: true,
                }}
                toolBarRender={() => [
                    <Authority value="white_resource_save">
                        <Button key="button" type="primary" icon={<PlusOutlined/>}
                                onClick={() => history.push('/admin/whiteResource/edit')}>
                            新建
                        </Button>
                    </Authority>,
                    <Authority value="white_resource_export">
                        <Button key="button" icon={<ExportOutlined/>}>
                            导出
                        </Button>
                    </Authority>,
                    <Authority value="white_resource_save">
                        <Button key="refresh"
                                onClick={() => initWhiteResourcePaths().then(() => message.success("初始化白名单资源列表成功"))}>
                            初始化白名单资源列表
                        </Button>
                    </Authority>,
                ]}
                tableAlertOptionRender={({selectedRowKeys}) => {
                    return (
                        <Space>
                            <Authority value="white_resource_delete">
                                <Popconfirm
                                    title="确定要删除吗？"
                                    okText="确定"
                                    cancelText="取消"
                                    onConfirm={() => onDeleteWhiteResources(selectedRowKeys as number[])}
                                >
                                    <Button key="batchDelete" icon={<MinusOutlined/>}>
                                        批量删除
                                    </Button>
                                </Popconfirm>
                            </Authority>
                            <Authority value="white_resource_change_enabled">
                                <Dropdown.Button overlay={
                                    <Menu
                                        onClick={(menuInfo) => onChangeWhiteResourcesEnabled(selectedRowKeys as number[], parseInt(menuInfo.key) === 1)}>
                                        <Menu.Item key={1}>批量启用</Menu.Item>
                                        <Menu.Item key={0}>批量禁用</Menu.Item>
                                    </Menu>
                                }
                                >
                                    启用 | 禁用
                                </Dropdown.Button>
                            </Authority>
                        </Space>
                    );
                }}
                columns={columns}
                request={
                    async (params, sort, filter) => {
                        const {current, pageSize, keyword} = params;
                        const result = await queryWhiteResourcePage(
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

export default WhiteResourceListPage;
