import {Avatar, List, Tabs} from "antd";
import React, {FC} from "react";

const {TabPane} = Tabs;

const Notice: FC = () => {
    const dataSource = {
        notice: [
            {
                avatar: "https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png",
                title: 'Ant Design Title 1',
                description: "Ant Design, a design language for background applications, is refined by Ant UED Team",
            },
            {
                avatar: "https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png",
                title: 'Ant Design Title 2',
                description: "Ant Design, a design language for background applications, is refined by Ant UED Team",
            },
            {
                avatar: "https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png",
                title: 'Ant Design Title 3',
                description: "Ant Design, a design language for background applications, is refined by Ant UED Team",
            },
            {
                avatar: "https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png",
                title: 'Ant Design Title 4',
                description: "Ant Design, a design language for background applications, is refined by Ant UED Team",
            },
        ],
        message: [
            {
                title: "你有新的订单需要审批",
            }
        ],
        todo: [
            {
                title: "你有新的订单需要审批",
            }
        ],
    };

    return (
        <Tabs defaultActiveKey="notice" style={{width: 400}}>
            <TabPane tab="通知" key="notice">
                <List itemLayout="horizontal" dataSource={dataSource.notice}
                    renderItem={item => (
                        <List.Item>
                            <List.Item.Meta
                                avatar={<Avatar src={item.avatar}/>}
                                title={<a href="https://ant.design">{item.title}</a>}
                                description={item.description}
                            />
                        </List.Item>
                    )}
                />
            </TabPane>
            <TabPane tab="消息" key="message">
                <List itemLayout="horizontal" dataSource={dataSource.message}
                    renderItem={item => (
                        <List.Item>
                            <List.Item.Meta title={<a href="https://ant.design">{item.title}</a>}/>
                        </List.Item>
                    )}
                />
            </TabPane>
            <TabPane tab="代办" key="todo">
                <List itemLayout="horizontal" dataSource={dataSource.todo}
                    renderItem={item => (
                        <List.Item>
                            <List.Item.Meta title={<a href="https://ant.design">{item.title}</a>}/>
                        </List.Item>
                    )}
                />
            </TabPane>
        </Tabs>
    )
}

export default Notice;