import React, {FC, useEffect, useState} from 'react';
import ProCard from '@ant-design/pro-card';
import {PageContainer} from "@ant-design/pro-layout";
import {useHistory, useParams} from "react-router-dom";
import {Button, Space} from "antd";
import ResourceAssignPanel from "../../resource/assign/ResourceAssignPanel";
import {assignRoleResourceActions, loadRoleAssignedResources} from "../../../services/role";
import {ResourceCategory} from "../../../models/ResourceCategory";

const RoleResourceAssignPage: FC = () => {
    const history = useHistory();
    const {id} = useParams<{ id: string }>();

    const [loading, setLoading] = useState<boolean>(true);
    const [resourceCategories, setResourceCategories] = useState<ResourceCategory[]>([]);
    const [resourceActionsMap, setResourceActionsMap] = useState<Map<number, number[]>>(new Map());

    useEffect(() => {
        const fetchData = async () => {
            const resourceCategories = await loadRoleAssignedResources(parseInt(id));
            setResourceCategories(resourceCategories || []);
            setResourceActionsMap(() => {
                const map = new Map<number, number[]>();
                resourceCategories.forEach(category => {
                    (category.resources || []).forEach(resource => {
                        map.set(resource.id as number, (resource.actions || []).filter(action => action.checked).map(action => action.id as number));
                    });
                });
                return map;
            })
            setLoading(false);
        }

        fetchData().then(() => {
        });
    }, []);

    const onSelectedChange = (resourceId: number, actionIds: number[]) => {
        setResourceActionsMap(pre => {
            pre.set(resourceId, actionIds);
            return pre;
        });
    };

    const onSaveSelected = () => {
        let allActionIds: number[] = [];
        resourceActionsMap.forEach((value) => allActionIds = allActionIds.concat(value));
        assignRoleResourceActions(Number(id), allActionIds).then(() => history.push('/admin/role'));
    };

    return (
        <PageContainer
            className="page-container"
            title="角色资源分配"
            header={{
                breadcrumb: {},
            }}
            extra={
                <Space>
                    <Button type="primary" onClick={onSaveSelected}>保存</Button>
                </Space>
            }
            onBack={() => history.push('/admin/role')}
        >
            <ProCard
                loading={loading}
                tabs={{
                    tabPosition: 'left',
                }}
            >
                {
                    resourceCategories.map(category => {
                        return (
                            <ProCard.TabPane key={category.id + ''} tab={category.categoryName}>
                                {
                                    category?.resources?.map(resource => {
                                        return (
                                            <ResourceAssignPanel key={resource.id + ''} resource={resource}
                                                                 onSelectedChange={onSelectedChange}/>
                                        )
                                    })
                                }
                            </ProCard.TabPane>
                        )
                    })
                }
            </ProCard>
        </PageContainer>
    );
};

export default RoleResourceAssignPage;