import { TreeSelect } from 'antd';
import React from "react";

class Demo extends React.Component {
    state = {
        value: undefined,
        treeData: [
            { id: 1, pId: 0, value: '1', title: 'Expand to load' },
            { id: 2, pId: 0, value: '2', title: 'Expand to load' },
            { id: 3, pId: 0, value: '3', title: 'Tree Node', isLeaf: true },
        ],
    };

    genTreeNode = (parentId: any, isLeaf = false) => {
        const random = Math.random().toString(36).substring(2, 6);
        return {
            id: random,
            pId: parentId,
            value: random,
            title: isLeaf ? 'Tree Node' : 'Expand to load',
            isLeaf,
        };
    };

    // @ts-ignore
    onLoadData = ({ id }) =>
        new Promise(resolve => {
            setTimeout(() => {
                this.setState({
                    // @ts-ignore
                    treeData: this.state.treeData.concat([
                        this.genTreeNode(id, false),
                        this.genTreeNode(id, true),
                    ]),
                });
                // @ts-ignore
                resolve();
            }, 300);
        });

    onChange = (value: any) => {
        console.log(value);
        this.setState({ value });
    };

    render() {
        const { treeData } = this.state;
        return (
            <TreeSelect
                treeDataSimpleMode
                style={{ width: '100%' }}
                value={this.state.value}
                dropdownStyle={{ maxHeight: 400, overflow: 'auto' }}
                placeholder="Please select"
                onChange={this.onChange}
                // @ts-ignore
                loadData={this.onLoadData}
                treeData={treeData}
            />
        );
    }
}

export default Demo;