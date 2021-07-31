import React from "react";
import {Transfer, Tree} from "antd";
import {DataNode} from "rc-tree/lib/interface";
import {TreeNode} from "../models/TreeNode";

type TreeTransferProps = {
    dataSource: TreeNode[];
    targetKeys: string[];
    onChange: (keys: string[]) => void;
};

const TreeTransfer = (props: TreeTransferProps) => {
    const {dataSource, targetKeys, onChange} = props;

    const transferDataSource: TreeNode[] = [];

    function flatten(list: TreeNode[]) {
        list.forEach(item => {
            transferDataSource.push(item);
            flatten(item.children || []);
        });
    }

    flatten(dataSource);

    const generateTree = (treeNodes: DataNode[] = [], checkedKeys: string[] = []): DataNode[] => {
        return treeNodes.map(({children, ...props}) => ({
            ...props,
            disabled: checkedKeys.includes(props.key as string),
            children: generateTree(children || [], checkedKeys),
        }))
    };

    const isChecked = (selectedKeys: string[], eventKey: string) => selectedKeys.indexOf(eventKey) !== -1;

    return (
        <Transfer<TreeNode>
            showSelectAll={false}
            onChange={onChange}
            dataSource={transferDataSource}
            targetKeys={targetKeys}
            render={item => item.title}
        >
            {({direction, onItemSelect, selectedKeys}) => {
                if (direction === 'left') {
                    const checkedKeys = [...selectedKeys, ...targetKeys];
                    return (
                        <Tree
                            blockNode
                            checkable
                            expandedKeys={['1']}
                            defaultExpandedKeys={['1', 1, '2', 2]}
                            checkedKeys={checkedKeys}
                            treeData={generateTree(dataSource.map(item => {
                                return {
                                    key: item.key,
                                    title: item.title,
                                    isLeaf: item.isLeaf,
                                    children: item.children,
                                } as DataNode;
                            }), targetKeys)}
                            onCheck={(_, {node: {key}}) => {
                                onItemSelect(key as string, !isChecked(checkedKeys, key as string));
                            }}
                            onSelect={(_, {node: {key}}) => {
                                onItemSelect(key as string, !isChecked(checkedKeys, key as string));
                            }}
                        />
                    );
                }
            }}
        </Transfer>
    );
};

export default TreeTransfer;