import React, {FC, useEffect, useState} from 'react';
import {Modal} from "antd";
import ProCard from '@ant-design/pro-card';
import OrganizationTree from "./OrganizationTree";
import UserList from "./UserList";
import './index.css';
import {User} from "../../../models/User";

type UserReferProps = {
    visible: boolean;
    defaultSelectedUserIds: number[];
    defaultSelectedUsers: User[];
    handleOk: (users: User[]) => void;
    handleCancel: () => void;
};

const UserReferPage: FC<UserReferProps> = (props) => {
    const {visible, defaultSelectedUserIds, defaultSelectedUsers, handleOk, handleCancel} = props;

    const [orgId, setOrgId] = useState<number>(1);
    const [selectedUserIds, setSelectedUserIds] = useState<number[]>(defaultSelectedUserIds);
    const [selectedUsers, setSelectedUsers] = useState<User[]>([]);

    useEffect(() => {
        setSelectedUserIds(defaultSelectedUserIds);
        setSelectedUsers(defaultSelectedUsers);
    }, [defaultSelectedUserIds, defaultSelectedUsers]);

    const onSelectedChange = (selectedRowKeys: number[], selectedRows: User[]) => {
        setSelectedUserIds(selectedRowKeys);
        setSelectedUsers(selectedRows);
    }

    return (
        <Modal title="选择用户" visible={visible} onOk={() => handleOk(selectedUsers)} onCancel={handleCancel} width={1200}>
            <div id="user-model-page">
                <ProCard split="vertical">
                    <ProCard colSpan={6} className="left">
                        <OrganizationTree orgId={orgId} onChange={(id) => setOrgId(id)}/>
                    </ProCard>
                    <ProCard>
                        <UserList orgId={orgId} selectedUserIds={selectedUserIds} handleSelectedChange={onSelectedChange}/>
                    </ProCard>
                </ProCard>
            </div>
        </Modal>
    );
};

export default UserReferPage;