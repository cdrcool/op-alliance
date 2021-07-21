import React, {FC} from 'react';
import ProCard from '@ant-design/pro-card';
import {PageContainer} from "@ant-design/pro-layout";
import {useHistory} from "react-router-dom";
import {Button, Checkbox, Collapse, List, Space} from "antd";

const {Panel} = Collapse;

const plainOptions = [
    {label: '保存', value: 'save'},
    {label: '删除', value: 'delete'},
    {label: '查看', value: 'view'},
    {label: '导出', value: 'export'},
];

const RoleAssignPage: FC = () => {
    const history = useHistory();

    const [checkedList, setCheckedList] = React.useState<string[]>([]);
    const [indeterminate, setIndeterminate] = React.useState(true);
    const [checkAll, setCheckAll] = React.useState(false);

    const onChange = (list: any[]) => {
        setCheckedList(list);
        setIndeterminate(!!list.length && list.length < plainOptions.length);
        setCheckAll(list.length === plainOptions.length);
    };

    const onCheckAllChange = (e: { target: { checked: boolean } }) => {
        console.log('e: ', e);
        // @ts-ignore
        setCheckedList(e.target.checked ? plainOptions : []);
        setIndeterminate(false);
        setCheckAll(e.target.checked);
    };

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
                <ProCard.TabPane key="management" tab="管理中心">
                    <Collapse activeKey={['organization', 'user', 'role', 'resourceCategory', 'resource', 'menu']}>
                        <Panel header={
                            <Space><Checkbox indeterminate={indeterminate} onChange={onCheckAllChange} checked={checkAll}/>组织管理</Space>
                        } key="organization" showArrow={false}>
                            <Checkbox.Group options={plainOptions} value={checkedList} onChange={onChange} />
                        </Panel>
                        <Panel header="用户管理" key="user" showArrow={false}>
                            <Checkbox.Group options={[
                                {label: '保存', value: 'save'},
                                {label: '删除', value: 'delete'},
                                {label: '查看', value: 'view'},
                                {label: '导出', value: 'export'},
                            ]}/>
                        </Panel>
                        <Panel header="角色管理" key="role" showArrow={false}>
                            <Checkbox.Group options={[
                                {label: '保存', value: 'save'},
                                {label: '删除', value: 'delete'},
                                {label: '查看', value: 'view'},
                                {label: '导出', value: 'export'},
                            ]}/>
                        </Panel>
                        <Panel header="资源分类管理" key="resourceCategory" showArrow={false}>
                            <Checkbox.Group options={[
                                {label: '保存', value: 'save'},
                                {label: '删除', value: 'delete'},
                                {label: '查看', value: 'view'},
                                {label: '导出', value: 'export'},
                            ]}/>
                        </Panel>
                        <Panel header="资源管理" key="resource" showArrow={false}>
                            <Checkbox.Group options={[
                                {label: '保存', value: 'save'},
                                {label: '删除', value: 'delete'},
                                {label: '查看', value: 'view'},
                                {label: '导出', value: 'export'},
                            ]}/>
                        </Panel>
                        <Panel header="菜单管理" key="menu" showArrow={false}>
                            <Checkbox.Group options={[
                                {label: '保存', value: 'save'},
                                {label: '删除', value: 'delete'},
                                {label: '查看', value: 'view'},
                                {label: '导出', value: 'export'},
                            ]}/>
                        </Panel>
                    </Collapse>
                </ProCard.TabPane>
                <ProCard.TabPane key="statistics" tab="统计中心">

                </ProCard.TabPane>
                <ProCard.TabPane key="logging" tab="日志中心">

                </ProCard.TabPane>
                <ProCard.TabPane key="monitor" tab="监控中心">

                </ProCard.TabPane>
                <ProCard.TabPane key="attachment" tab="附件中心">

                </ProCard.TabPane>
                <ProCard.TabPane key="document" tab="文档中心">

                </ProCard.TabPane>
            </ProCard>
        </PageContainer>
    );
};

export default RoleAssignPage;