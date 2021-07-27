import React from 'react';
import {BrowserRouter} from 'react-router-dom';
import {renderRoutes} from 'react-router-config';
import {ConfigProvider} from 'antd';
import 'moment/locale/zh-cn';
import locale from 'antd/lib/locale/zh_CN';
import routes from './router';

const App = () => {
    return (
        // 全局化配置（https://ant.design/components/config-provider-cn/#header）
        <ConfigProvider locale={locale}>
            <BrowserRouter>
                {renderRoutes(routes)}
            </BrowserRouter>
        </ConfigProvider>
    )
}
export default App;