import React, {useEffect, useState} from "react";
import {Transfer, Tree} from "antd";
import {DataNode, EventDataNode} from "rc-tree/lib/interface";
import {TreeNode} from "../models/TreeNode";

type TreeTransferProps = {
    dataSource: TreeNode[];
    targetKeys: string[];
    onChange: (keys: string[]) => void;
    onLoadData: (node: EventDataNode) => Promise<void>;
};

const flatten = (list: TreeNode[], flatList: TreeNode[]) => {
    list && list.forEach(item => {
        flatList.push(item);
        flatten(item.children as [], flatList);
    });
};

const generateTree = (treeNodes: DataNode[] = [], checkedKeys: string[] = []): DataNode[] => {
    return treeNodes.map(({children, ...props}) => ({
        ...props,
        disabled: checkedKeys.includes(props.key as string),
        children: children && generateTree(children, checkedKeys),
    }))
};

const isChecked = (selectedKeys: string[], eventKey: string) => selectedKeys.indexOf(eventKey) !== -1;

const TreeTransfer = (props: TreeTransferProps) => {
    const {dataSource, targetKeys, onChange, onLoadData} = props;

    const transferDataSource: TreeNode[] = [];
    flatten(dataSource, transferDataSource);

    const [expandedKeys, setExpandedKeys] = useState<string[]>(['1']);
    useEffect(() => {
        setExpandedKeys(['1']);
    }, []);

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
                            expandedKeys={expandedKeys}
                            checkedKeys={checkedKeys}
                            treeData={
                                generateTree(dataSource.map(item => {
                                    return {
                                        key: item.key,
                                        title: item.title,
                                        isLeaf: item.isLeaf,
                                        children: item.children,
                                    } as DataNode;
                                }), targetKeys)
                            }
                            loadData={onLoadData}
                            onCheck={(_, {node: {key}}) => {
                                onItemSelect(key as string, !isChecked(checkedKeys, key as string));
                            }}
                            onSelect={(_, {node: {key}}) => {
                                onItemSelect(key as string, !isChecked(checkedKeys, key as string));
                            }}
                            onExpand={(expandedKeys) => setExpandedKeys(expandedKeys as string[])}
                        />
                    );
                }
            }}
        </Transfer>
    );
};

export default TreeTransfer;