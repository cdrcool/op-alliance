import {Button, Card, Collapse, Form, Input, InputNumber, Popconfirm, Select, Space, Spin} from 'antd';
import {useHistory, useParams} from "react-router-dom";
import React, {FC, useEffect, useState} from "react";
import {PageContainer} from "@ant-design/pro-layout";
import {getResource, saveResource} from "../../../services/resource";
import {queryResourceCategorySelectOptions} from "../../../services/resourceCategory";
import {SelectOptions} from "../../../models/SelectOptions";
import {ResourceAction} from "../../../models/ResourceAction";
import {EditableProTable} from "@ant-design/pro-table";

const {Option} = Select;
const {Panel} = Collapse;
const {TextArea} = Input;

const ResourceEditPage: FC = () => {
    const history = useHistory();
    const {id} = useParams<{ id?: string }>();
    // @ts-ignore
    const {categoryId} = history.location.state || {};

    const [loading, setLoading] = useState<boolean>(!!id);
    const [categoryOptions, setCategoryOptions] = useState<SelectOptions[]>([]);
    const [resourceActions, setResourceActions] = useState<ResourceAction[]>([]);
    const [form] = Form.useForm();

    useEffect(() => {
        if (id) {
            const fetchData = async () => {
                const resource = await getResource(parseInt(id));
                const {actions, ...fieldValues} = resource;
                form.setFieldsValue(fieldValues);
                setResourceActions(actions || []);
                setLoading(false);
            }

            fetchData().then(() => {
            });
        } else {
            form.setFieldsValue({categoryId});
        }

        const fetchCategoryOptions = async () => {
            const categoryOptions = await queryResourceCategorySelectOptions({});
            setCategoryOptions(categoryOptions || []);
        };
        fetchCategoryOptions().then(() => {
        });

    }, []);

    const onSave = () => {
        form.validateFields().then(values => {
            saveResource({
                ...values,
                actions: resourceActions.map(item => {
                    if (item.isAdd) {
                        const {id, ...others} = item;
                        return others;
                    }
                    return item;
                }),
            }).then(() => {
                history.push('/admin/resource');
            });
        });
    };

    const onDeleteResourceActions = (actionId: number) => {
        setResourceActions(resourceActions.filter((item) => item.id !== actionId));
    }

    return (
        <PageContainer
            className="page-container"
            title={`${id ? '??????' : '??????'}??????`}
            header={{
                breadcrumb: {},
            }}
            extra={
                <Space>
                    <Button type="primary" onClick={onSave}>??????</Button>
                </Space>
            }
            onBack={() => history.push('/admin/resource')}

        >
            <Spin spinning={loading}>
                <Card>
                    <Collapse bordered={false} ghost={true} activeKey={['baseInfo', 'actions']}>
                        <Panel header="????????????" key="baseInfo" showArrow={false}>
                            <Form
                                form={form}
                                labelCol={{span: 8}}
                                wrapperCol={{span: 8}}
                            >
                                <Form.Item name="id" hidden={true}/>
                                <Form.Item label="????????????" name="categoryId">
                                    <Select>
                                        {
                                            categoryOptions.map(item => <Option key={item.value}
                                                                                value={item.value}>{item.label}</Option>)
                                        }
                                    </Select>
                                </Form.Item>
                                <Form.Item label="?????????" name="resourceName"
                                           rules={[{required: true, message: '??????????????????'}]}>
                                    <Input/>
                                </Form.Item>
                                <Form.Item label="????????????" name="resourcePath"
                                           rules={[{required: true, message: '?????????????????????'}]}>
                                    <Input/>
                                </Form.Item>
                                <Form.Item label="????????????" name="resourceDesc">
                                    <TextArea/>
                                </Form.Item>
                                <Form.Item label="????????????" name="resourceNo">
                                    <InputNumber/>
                                </Form.Item>
                            </Form>
                        </Panel>
                        <Panel header="??????????????????" key="actions" showArrow={false}>
                            <EditableProTable<ResourceAction>
                                rowKey="id"
                                options={{
                                    reload: false,
                                    fullScreen: true,
                                }}
                                columns={[
                                    {title: '????????????', dataIndex: 'actionName'},
                                    {title: '????????????', dataIndex: 'actionPath'},
                                    {title: '????????????', dataIndex: 'permission'},
                                    {title: '????????????', dataIndex: 'actionNo', width: 80},
                                    {
                                        title: '??????',
                                        valueType: 'option',
                                        render: (text, record, _, action) => [
                                            <a key="edit" onClick={
                                                () => action?.startEditable?.(record.id as number)
                                            }>
                                                ??????
                                            </a>,
                                            <a key="delete" onClick={
                                                () => onDeleteResourceActions(record.id as number)
                                            }>
                                                ??????
                                            </a>,
                                        ],
                                    },
                                ]}
                                editable={
                                    {
                                        type: 'multiple',
                                    }
                                }
                                recordCreatorProps={
                                    {
                                        record: {
                                            id: Date.now(),
                                            isAdd: true,
                                        },
                                        creatorButtonText: '??????????????????',
                                    }
                                }
                                value={resourceActions}
                                onChange={setResourceActions}
                            />
                        </Panel>
                    </Collapse>
                </Card>
            </Spin>
        </PageContainer>
    );
};

export default ResourceEditPage;