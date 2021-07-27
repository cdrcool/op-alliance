import React, {FC, useState} from 'react';
import {PageContainer} from "@ant-design/pro-layout";
import ProCard from '@ant-design/pro-card';
import OrganizationTree from "./OrganizationTree";
import UserList from "./UserList";
import './index.css';

const UserListPage: FC = () => {
    const [orgId, setOrgId] = useState<number>(1);

    return (
        <PageContainer
            className="page-container"
            header={{
                breadcrumb: {},
            }}
        >
            <div id="user-page">
                <ProCard split="vertical">
                    <ProCard colSpan={6} className="left">
                        <OrganizationTree orgId={orgId} onChange={(id) => setOrgId(id)}/>
                    </ProCard>
                    <ProCard>
                        <UserList orgId={orgId}/>
                    </ProCard>
                </ProCard>
            </div>
        </PageContainer>
    );
};

export default UserListPage;