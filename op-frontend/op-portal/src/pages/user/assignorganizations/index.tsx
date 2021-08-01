import React, {useEffect, useState} from 'react';
import {useHistory, useParams} from "react-router-dom";
import {Button, Space} from 'antd';
import {PageContainer} from "@ant-design/pro-layout";
import {queryForOrganizationAsyncTree} from "../../../services/organization";
import {TreeNode} from "../../../models/TreeNode";
import TreeTransfer from "../../../components/TreeTransfer";
import {EventDataNode} from "rc-tree/lib/interface";
import {assignUserOrganizations, getUserAssignedOrganizationIds} from "../../../services/user";

const UserAssignOrganizationsPage = () => {
    const history = useHistory();
    const {id} = useParams<{ id: string }>();

    const [treeData, setTreeData] = useState<TreeNode[]>([]);

    useEffect(() => {
        const fetchTreeData = async () => {
            const treeData = await queryForOrganizationAsyncTree({});
            setTreeData(treeData || []);
        };

        fetchTreeData().then(() => {
        });

        const fetchOrganizationIds = async () => {
            const organizationIds = await getUserAssignedOrganizationIds(Number(id));
            setTargetKeys((organizationIds || []).map(orgId => orgId + ''));
        };

        fetchOrganizationIds().then(() => {
        });
    }, []);

    const [targetKeys, setTargetKeys] = useState<string[]>([]);
    const onChange = (keys: string[]) => {
        setTargetKeys(keys);
    };

    const onLoadData = async (node: EventDataNode) => {
        const {key, children} = node;

        if (children) {
            return;
        }

        const treeNodes = await queryForOrganizationAsyncTree({
            pid: key,
        });
        setTreeData((origin) =>
            updateTreeData(origin, key as string, treeNodes),
        );
    };

    const updateTreeData = (list: TreeNode[], key: string, children: TreeNode[]): TreeNode[] => {
        return list.map((node) => {
            if (node.key === key) {
                return {...node, children};
            }

            if (node.children) {
                return {...node, children: updateTreeData(node.children, key, children)};
            }

            return node;
        });
    }

    const onSaveSelected = () => {
        assignUserOrganizations(Number(id), targetKeys.map(key => Number(key))).then(() => {
            history.push('/admin/user');
        });
    };

    return <PageContainer
        className="page-container"
        title="用户组织授权"
        header={{
            breadcrumb: {},
        }}
        extra={
            <Space>
                <Button type="primary" onClick={onSaveSelected}>保存</Button>
            </Space>
        }
        onBack={() => history.push('/admin/user')}
    >
        <TreeTransfer dataSource={treeData} targetKeys={targetKeys} onChange={onChange} onLoadData={onLoadData}/>
    </PageContainer>
};

export default UserAssignOrganizationsPage;
