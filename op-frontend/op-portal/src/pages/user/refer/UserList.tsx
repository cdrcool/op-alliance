import React, {FC, useEffect, useRef, useState} from "react";
import ProTable, {ActionType, ProColumns} from "@ant-design/pro-table";
import {User} from "../../../models/User";
import {queryUserPage} from "../../../services/user";

type UserListProps = {
    orgId: number;
    selectedUserIds: number[];
    handleSelectedChange: (selectedRowKeys: number[], selectedRows: User[]) => void;
};

const UserList: FC<UserListProps> = (props) => {
    const {orgId, selectedUserIds, handleSelectedChange} = props;

    const ref = useRef<ActionType>();

    useEffect(() => {
        // @ts-ignore
        ref.current.reload();
    }, [orgId]);

    const userColumns: ProColumns<User>[] = [
        {
            title: '用户名',
            dataIndex: 'username',
        },
        {
            title: '昵称',
            dataIndex: 'nickname',
        },
        {
            title: '手机号',
            dataIndex: 'phone',
        },
        {
            title: '邮箱',
            dataIndex: 'email',
        },
        {
            title: '帐号状态',
            dataIndex: 'status',
            valueEnum: {
                0: {text: '禁用', status: 'Error'},
                1: {text: '启用', status: 'Success'},
                2: {text: '过期', status: 'Error'},
                3: {text: '锁定', status: 'Error'},
                4: {text: '密码过期', status: 'Error'},
            },
            filters: true,
            onFilter: true,
        },
    ];

    return (
        <ProTable<User>
            actionRef={ref}
            rowKey="id"
            search={false}
            options={{
                search: {
                    placeholder: "输入用户名、昵称、手机号或邮箱查询",
                    style: {width: 400},
                },
                fullScreen: true,
            }}
            columns={userColumns}
            request={
                async (params, sort, filter) => {
                    const {current, pageSize, keyword} = params;
                    const result = await queryUserPage(
                        (current || 1) - 1,
                        pageSize || 10,
                        {
                            orgId,
                            keyword,
                            ...filter
                        }
                    );
                    return {
                        data: result.content,
                        success: true,
                        total: result.totalElements,
                    };
                }
            }
            rowSelection={
                {
                    selectedRowKeys: selectedUserIds,
                    onChange: (selectedRowKeys, selectedRows) => {
                        handleSelectedChange(selectedRowKeys as number[], selectedRows);
                    },
                }
            }
            pagination={{
                pageSize: 10,
            }}
        />
    )
};

export default UserList;