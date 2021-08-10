import React from 'react';
import {useHistory} from "react-router-dom";
import {Button, Result} from 'antd';

const UnauthorizedPage: React.FC = () => {
    const history = useHistory();

    return (
        (

            <Result
                status="403"
                title="403"
                subTitle="抱歉，您没有权限访问改页面"
                extra={
                    <Button type="primary" onClick={() => history.push('/')}>
                        回到首页
                    </Button>
                }
            />
        )
    );
};

export default UnauthorizedPage;
