import {Button, Card, Collapse, Form, Input, InputNumber, Popconfirm, Select, Space, Spin} from 'antd';
import {useHistory, useParams} from "react-router-dom";
import React, {FC, useEffect, useState} from "react";
import {PageContainer} from "@ant-design/pro-layout";
import {getResource, saveResource} from "../../../services/resource";
import {queryResourceCategorySelectList} from "../../../services/resourceCategory";
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
            const categoryOptions = await queryResourceCategorySelectList({});
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
            title={`${id ? '编辑' : '新增'}资源`}
            header={{
                breadcrumb: {},
            }}
            extra={
                <Space>
                    <Button type="primary" onClick={onSave}>保存</Button>
                </Space>
            }
            onBack={() => history.push('/admin/resource')}

        >
            <Spin spinning={loading}>
                <Card>
                    <Collapse bordered={false} ghost={true} activeKey={['baseInfo', 'actions']}>
                        <Panel header="基本信息" key="baseInfo" showArrow={false}>
                            <Form
                                form={form}
                                labelCol={{span: 8}}
                                wrapperCol={{span: 8}}
                            >
                                <Form.Item name="id" hidden={true}/>
                                <Form.Item label="资源分类" name="categoryId">
                                    <Select>
                                        {
                                            categoryOptions.map(item => <Option key={item.value}
                                                                                value={item.value}>{item.label}</Option>)
                                        }
                                    </Select>
                                </Form.Item>
                                <Form.Item label="资源名" name="resourceName"
                                           rules={[{required: true, message: '请输入资源名'}]}>
                                    <Input/>
                                </Form.Item>
                                <Form.Item label="资源路径" name="resourcePath"
                                           rules={[{required: true, message: '请输入资源路径'}]}>
                                    <Input/>
                                </Form.Item>
                                <Form.Item label="资源描述" name="resourceDesc">
                                    <TextArea/>
                                </Form.Item>
                                <Form.Item label="资源编号" name="resourceNo">
                                    <InputNumber/>
                                </Form.Item>
                            </Form>
                        </Panel>
                        <Panel header="资源动作列表" key="actions" showArrow={false}>
                            <EditableProTable<ResourceAction>
                                rowKey="id"
                                columns={[
                                    {title: '动作名称', dataIndex: 'actionName'},
                                    {title: '动作路径', dataIndex: 'actionPath'},
                                    {title: '权限标识', dataIndex: 'permission'},
                                    {title: '动作编号', dataIndex: 'actionNo', width: 80},
                                    {
                                        title: '操作',
                                        valueType: 'option',
                                        render: (text, record, _, action) => [
                                            <a key="edit" onClick={
                                                () => action?.startEditable?.(record.id as number)
                                            }>
                                                编辑
                                            </a>,
                                            <Popconfirm
                                                title="删除此行？"
                                                okText="确定"
                                                cancelText="取消"
                                                onConfirm={
                                                    () => onDeleteResourceActions(record.id as number)
                                                }
                                            >
                                                <a key="delete">
                                                    删除
                                                </a>
                                            </Popconfirm>,
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
                                        creatorButtonText: '新增资源动作',
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