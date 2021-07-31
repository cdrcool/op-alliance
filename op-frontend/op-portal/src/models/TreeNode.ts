export interface TreeNode {
    key: string;
    id: number;
    pid: number;
    title: string;
    value: string | number;
    isLeaf?: boolean;
    isExpand?: boolean;
    children?: TreeNode[];
}