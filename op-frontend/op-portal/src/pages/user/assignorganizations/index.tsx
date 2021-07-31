import React, {useEffect, useState} from 'react';
import {useHistory, useParams} from "react-router-dom";
import {Button, Space} from 'antd';
import {PageContainer} from "@ant-design/pro-layout";
import {queryOrganizationTreeReferList} from "../../../services/organization";
import {TreeNode} from "../../../models/TreeNode";
import TreeTransfer from "../../../components/TreeTransfer";

const UserAssignOrganizationsPage = () => {
    const history = useHistory();
    const {id} = useParams<{ id: string }>();

    const [treeData, setTreeData] = useState<TreeNode[]>([]);

    useEffect(() => {
        const fetchTreeData = async () => {
            const treeData = await queryOrganizationTreeReferList({});
            setTreeData(treeData || []);
        };

        fetchTreeData().then(() => {
        });
    }, []);

    const [targetKeys, setTargetKeys] = useState<string[]>([]);
    const onChange = (keys: string[]) => {
        setTargetKeys(keys);
    };

    const onSaveSelected = () => {
        console.log('targetKeys: ', targetKeys);
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
        <TreeTransfer dataSource={treeData} targetKeys={targetKeys} onChange={onChange}/>
    </PageContainer>
};

export default UserAssignOrganizationsPage;
