import React, {FC, useRef} from "react";
import {useHistory} from "react-router-dom";
import {Button, Dropdown, Menu, Popconfirm, Space} from "antd";
import {ExportOutlined, MinusOutlined, PlusOutlined} from "@ant-design/icons";
import {PageContainer} from "@ant-design/pro-layout";
import type {ActionType, ProColumns} from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import {Menus} from "../../../models/Menus";
import {changeMenusVisibility, deleteMenus, queryMenuTreeList} from "../../../services/menu";
import Authority from "../../../components/Authority";
import AuthTableDropDown from "../../../components/AuthTableDropdown";

const MenuListPage: FC = () => {
    const history = useHistory();
    const ref = useRef<ActionType>();

    const onDeleteMenus = (ids: number[]) => {
        // @ts-ignore
        deleteMenus(ids).then(() => ref.current.reloadAndRest());
    };

    const onChangeMenusVisibility = (ids: number[], show: boolean) => {
        // @ts-ignore
        changeMenusVisibility(ids, show).then(() => ref.current.reload());
    };

    const columns: ProColumns<Menus>[] = [
        {
            title: '菜单名称',
            dataIndex: 'menuName',
        },
        {
            title: '菜单路径',
            dataIndex: 'menuPath',
        },
        {
            title: '菜单图标',
            dataIndex: 'menuIcon',
        },
        {
            title: '是否显示',
            dataIndex: 'isShow',
            valueEnum: {
                true: {text: '是', status: 'Success'},
                false: {text: '否', status: 'Warning'},
            },
        },
        {
            title: '权限标识',
            dataIndex: 'permission',
        },
        {
            title: '操作',
            valueType: 'option',
            render: (text, record, _, action) => [
                <Authority value="menu_save">
                    <a key="edit" onClick={() => history.push(`/admin/menu/edit/${record.id}`)}>
                        编辑
                    </a>
                </Authority>,
                <Authority value="menu_delete">
                    <Popconfirm
                        title="确定要删除吗？"
                        okText="确定"
                        cancelText="取消"
                        onConfirm={() => onDeleteMenus([record.id] as number[])}
                    >
                        <a key="delete">
                            删除
                        </a>
                    </Popconfirm>
                </Authority>,
                <Authority value="menu_view">
                    <a key="view" onClick={() => history.push(`/admin/menu/detail/${record.id}`)}>
                        查看
                    </a>
                </Authority>,
                <Authority value="menu_save">
                    <a key="addChild" onClick={() => history.push(`/admin/menu/edit`, {
                        pid: record.id,
                    })}>
                        新增下级
                    </a>
                </Authority>,
                <AuthTableDropDown
                    key="actions"
                    menus={[
                        {
                            // @ts-ignore
                            menu: 'menu_change_visibility',
                            key: 'enable',
                            name: record.isShow ? '隐藏' : '显示',
                            onClick: () => onChangeMenusVisibility([record.id] as number[], !record.isShow),
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
            <ProTable<Menus>
                actionRef={ref}
                rowKey="id"
                search={false}
                options={{
                    search: {
                        placeholder: "输入菜单名称、菜单路径或权限标识查询",
                        style: {width: 400},
                    },
                    fullScreen: true,
                }}
                toolBarRender={() => [
                    <Authority value="menu_save">
                        <Button key="button" type="primary" icon={<PlusOutlined/>}
                                onClick={() => history.push('/admin/menu/edit')}>
                            新建
                        </Button>
                    </Authority>,
                    <Authority value="menu_export">
                        <Button key="button" icon={<ExportOutlined/>}>
                            导出
                        </Button>
                    </Authority>,
                ]}
                tableAlertOptionRender={({selectedRowKeys}) => {
                    return (
                        <Space>
                            <Authority value="menu_save">
                                <Popconfirm
                                    title="确定要删除吗？"
                                    okText="确定"
                                    cancelText="取消"
                                    onConfirm={() => onDeleteMenus(selectedRowKeys as number[])}
                                >
                                    <Button key="batchDelete" icon={<MinusOutlined/>}>
                                        批量删除
                                    </Button>
                                </Popconfirm>
                            </Authority>
                            <Authority value="menu_change_visibility">
                                <Dropdown.Button overlay={
                                    <Menu
                                        onClick={(menuInfo) => onChangeMenusVisibility(selectedRowKeys as number[], parseInt(menuInfo.key) === 1)}>
                                        <Menu.Item key={1}>批量显示</Menu.Item>
                                        <Menu.Item key={0}>批量隐藏</Menu.Item>
                                    </Menu>
                                }
                                >
                                    显示 | 隐藏
                                </Dropdown.Button>
                            </Authority>
                        </Space>
                    );
                }}
                columns={columns}
                request={
                    async (params, sort, filter) => {
                        const {keyword} = params;
                        const result = await queryMenuTreeList(
                            {
                                keyword,
                                ...filter
                            }
                        );
                        return {
                            data: result,
                            success: true,
                        };
                    }}
                rowSelection={{}}
                pagination={false}
            />
        </PageContainer>
    )
};

export default MenuListPage;
