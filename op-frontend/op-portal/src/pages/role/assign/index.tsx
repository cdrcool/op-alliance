import React from 'react';
import ProCard from '@ant-design/pro-card';
import {PageContainer} from "@ant-design/pro-layout";
import {useHistory} from "react-router-dom";
import {Button, Checkbox, Collapse, Space} from "antd";
import {Resource} from "../../../models/Resource";
import resourceCategories from "../../../services/resourceCategoryJson";

const {Panel} = Collapse;

const ResourcePanel: React.FC<Resource> = (props) => {
    const {resourceName, resourceEnName, actions=[]} = props;

    const [checkedList, setCheckedList] = React.useState<string[]>([]);
    const [indeterminate, setIndeterminate] = React.useState(true);
    const [checkAll, setCheckAll] = React.useState(false);

    const onChange = (list: any[]) => {
        setCheckedList(list);
        setIndeterminate(!!list.length && list.length < actions.length);
        setCheckAll(list.length === actions.length);
    };

    const onCheckAllChange = (e: { target: { checked: boolean } }) => {
        // @ts-ignore
        setCheckedList(e.target.checked ? actions.map(item => item.permission) : []);
        setIndeterminate(false);
        setCheckAll(e.target.checked);
    };

    return (
        <Panel
            key={resourceEnName || "1"}
            showArrow={false}
            header={
                <Space>
                    <Checkbox
                        indeterminate={indeterminate}
                        onChange={onCheckAllChange}
                        checked={checkAll}
                    />
                    {resourceName}
                </Space>
            }
        >
            <Checkbox.Group
                // @ts-ignore
                options={
                    actions.map(action => {
                        return {
                            label: action.actionName,
                            value: action.permission,
                        }
                    })
                }
                value={checkedList}
                onChange={onChange}/>
        </Panel>
    )
};

const RoleAssignPage: React.FC = () => {
    const history = useHistory();

    const onSave = () => {

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
                    <Button type="primary" onClick={onSave}>保存</Button>
                </Space>
            }
            onBack={() => history.push('/admin/role')}
        >
            <ProCard
                tabs={{
                    tabPosition: 'left',
                }}
            >
                {
                    resourceCategories.map(category => {
                        console.log(category.categoryEnName);
                        return (
                            <ProCard.TabPane key={category.categoryEnName} tab={category.categoryName}>
                                <Collapse activeKey={category.resources?.map(resource => resource.resourceEnName|| "11")}>
                                    {
                                        category?.resources?.map(resource => {
                                            return (
                                                // @ts-ignore
                                                <ResourcePanel resource={resource}/>
                                            )
                                        })
                                    }
                                </Collapse>
                            </ProCard.TabPane>
                        )
                    })
                }
            </ProCard>
        </PageContainer>
    );
};

export default RoleAssignPage;