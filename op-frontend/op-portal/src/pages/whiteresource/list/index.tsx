import React, {FC, useRef} from "react";
import {useHistory} from "react-router-dom";
import {Button, Dropdown, Menu, message, Popconfirm, Space} from "antd";
import {ExportOutlined, MinusOutlined, PlusOutlined} from "@ant-design/icons";
import {PageContainer} from "@ant-design/pro-layout";
import type {ActionType, ProColumns} from '@ant-design/pro-table';
import ProTable, {TableDropdown} from '@ant-design/pro-table';
import {Role} from "../../../models/Role";
import {WhiteResource} from "../../../models/WhiteResource";
import {
    changeWhiteResourcesEnabled,
    deleteWhiteResources, initWhiteResourcePaths,
    queryWhiteResourcePage
} from "../../../services/whiteResource";
import {initResourcePathPermissions} from "../../../services/resourceAction";

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
                <a key="edit" onClick={() => history.push(`/admin/whiteResource/edit/${record.id}`)}>
                    编辑
                </a>,
                <Popconfirm
                    title="确定要删除吗？"
                    okText="确定"
                    cancelText="取消"
                    onConfirm={() => onDeleteWhiteResources([record.id] as number[])}
                >
                    <a key="delete">
                        删除
                    </a>
                </Popconfirm>,
                <a key="view" onClick={() => history.push(`/admin/whiteResource/detail/${record.id}`)}>
                    查看
                </a>,
                <TableDropdown
                    key="actions"
                    menus={[
                        {
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
                    <Button key="button" type="primary" icon={<PlusOutlined/>}
                            onClick={() => history.push('/admin/whiteResource/edit')}>
                        新建
                    </Button>,
                    <Button key="button" icon={<ExportOutlined/>}>
                        导出
                    </Button>,
                    <Button key="refresh" onClick={() => initWhiteResourcePaths().then(() => message.success("初始化白名单资源列表成功"))}>
                        初始化白名单资源列表
                    </Button>,
                ]}
                tableAlertOptionRender={({selectedRowKeys}) => {
                    return (
                        <Space>
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
