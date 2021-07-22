import {Button, Card, Descriptions, Popconfirm, Space, Spin} from 'antd';
import {useHistory, useParams} from "react-router-dom";
import React, {FC, useEffect, useState} from "react";
import {PageContainer} from "@ant-design/pro-layout";
import {deleteMenu, getMenu} from "../../../services/menu";
import {Menus} from "../../../models/Menus";

const MenuDetailPage: FC = () => {
    const history = useHistory();
    const {id} = useParams<{ id: string }>();

    const [loading, setLoading] = useState<boolean>(true);
    const [menu, setMenu] = useState<Menus>({});

    useEffect(() => {
        const fetchData = async () => {
            const menu = await getMenu(parseInt(id));
            setMenu(menu || {});
            setLoading(false);
        }

        fetchData().then(() => {
        });
    }, []);

    const onDeleteMenu = (id: string) => {
        deleteMenu(parseInt(id)).then(() => history.push('/admin/menu'));
    }

    return (
        <PageContainer
            className="page-container"
            title="菜单详情"
            header={{
                breadcrumb: {},
            }}
            extra={
                <Space>
                    <Button type="primary" onClick={() => history.push(`/admin/menu/edit/${id}`)}>编辑</Button>
                    <Popconfirm
                        title="确定要删除吗？"
                        okText="确定"
                        cancelText="取消"
                        onConfirm={() => onDeleteMenu(id)}
                    >
                        <Button>删除</Button>
                    </Popconfirm>
                    <Button>打印</Button>
                </Space>
            }
            onBack={() => history.push('/admin/menu')}

        >
            <Spin spinning={loading}>
                <Card>
                    <Descriptions>
                        <Descriptions.Item label="菜单名">{menu.menuName}</Descriptions.Item>
                        <Descriptions.Item label="菜单图标">{menu.menuIcon}</Descriptions.Item>
                        <Descriptions.Item label="菜单路径">{menu.menuPath}</Descriptions.Item>
                        <Descriptions.Item
                            label="是否隐藏">{menu.isHidden === true ? '是' : (menu.isHidden === false ? '否' : null)}</Descriptions.Item>
                        <Descriptions.Item label="权限标识">{menu.permission}</Descriptions.Item>
                        <Descriptions.Item label="菜单编号">{menu.menuNo}</Descriptions.Item>
                    </Descriptions>
                </Card>
            </Spin>
        </PageContainer>
    );
};

export default MenuDetailPage;