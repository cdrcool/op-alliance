import React, {FC, useEffect, useState} from "react";
import ProTable, {ProColumns} from "@ant-design/pro-table";
import {Organization} from "../../../models/Organization";
import {queryOrganizationTree} from "../../../services/organization";

type OrganizationTreeProps = {
    orgId: number;
    onChange: (id: number) => void;
};

const OrganizationTree: FC<OrganizationTreeProps> = (props) => {
    const {orgId, onChange} = props;

    const [rowKey, setRowKey] = useState<number>(1);

    // 动态调整组织树的滚动条高度
    const getWindowSize = () => ({
        innerHeight: document.body.scrollHeight,
        innerWidth: document.body.scrollWidth,
    });
    const [windowSize, setWindowSize] = useState(getWindowSize());
    const handleResize = () => {
        setWindowSize(getWindowSize());
    };
    useEffect(() => {
        // 监听
        window.addEventListener("resize", handleResize);
        // 销毁
        return () => window.removeEventListener("resize", handleResize);
    });

    const orgColumns: ProColumns<Organization>[] = [
        {
            title: '组织名称',
            dataIndex: 'orgName',
        },
    ];

    return (
        <ProTable<Organization>
            rowKey="id"
            search={false}
            options={{
                search: {
                    placeholder: "输入组织名称查询",
                    style: {width: 200},
                },
                fullScreen: false,
                reload: false,
                setting: false,
                density: false,
            }}
            expandable={
                {
                    defaultExpandedRowKeys: [orgId],
                }
            }
            scroll={{y: windowSize.innerHeight - 300}}
            rowClassName={(record, index) => {
                return record.id === rowKey ? "rowSelected" : "rowUnSelected"
            }}
            onRow={
                (record, rowIndex) => {
                    return {
                        onClick: () => {
                            setRowKey(record.id as number);
                            onChange(record.id as number);
                        }
                    };
                }}
            columns={orgColumns}
            request={
                async (params) => {
                    const {keyword} = params;
                    const result = await queryOrganizationTree(
                        {
                            keyword,
                        }
                    );
                    return {
                        data: [result],
                        success: true,
                    };
                }}
            pagination={false}
        />
    )
};

export default OrganizationTree;