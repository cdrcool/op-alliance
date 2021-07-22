import React, {FC, useState} from "react";
import {Resource} from "../../../models/Resource";
import {Card, Checkbox, Space} from "antd";
import {CheckboxOptionType, CheckboxValueType} from "antd/es/checkbox/Group";

type ResourceAssignPanelProps = {
    resource: Resource,
    onSelectedChange: (resourceId: number, actionIds: number[]) => void,
}

const ResourceAssignPanel: FC<ResourceAssignPanelProps> = (props) => {
    const {resource, onSelectedChange} = props;
    const {id, resourceName, actions} = resource;
    const groupOptions = actions || [];
    const selectedOptions = groupOptions.filter(action => action.checked).map(action => action.id as number);

    const [checkedList, setCheckedList] = useState<number[]>(selectedOptions);
    const [indeterminate, setIndeterminate] = useState(!!selectedOptions.length && selectedOptions.length < groupOptions.length);
    const [checkAll, setCheckAll] = useState(selectedOptions.length > 0 && selectedOptions.length === groupOptions.length);

    const onChange = (list: CheckboxValueType[]) => {
        console.log('list: ', list);
        setCheckedList(list as number[]);
        setIndeterminate(!!list.length && list.length < groupOptions.length);
        setCheckAll(list.length === groupOptions.length);
        onSelectedChange(id as number, list as number[]);
    };

    const onCheckAllChange = (e: { target: { checked: boolean } }) => {
        setCheckedList(e.target.checked ? groupOptions.map(item => item.id as number) : []);
        setIndeterminate(false);
        setCheckAll(e.target.checked);
        onSelectedChange(id as number, e.target.checked ? groupOptions.map(item => item.id as number) : []);
    };

    return (
        <Card
            headStyle={{backgroundColor: '#fafafa'}}
            title={
                <Space>
                    <Checkbox
                        indeterminate={indeterminate}
                        checked={checkAll}
                        onChange={onCheckAllChange}
                    />
                    {resourceName}
                </Space>
            }
        >
            <Checkbox.Group
                options={
                    groupOptions.map(action => {
                        return {
                            label: action.actionName,
                            value: action.id,
                            disabled: !action.enableUncheck,
                        } as CheckboxOptionType
                    })
                }
                value={checkedList}
                onChange={onChange}/>
        </Card>
    )
};

export default ResourceAssignPanel;