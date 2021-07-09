import React from 'react'
import {renderRoutes} from 'react-router-config'
import {BrowserRouter as Routers} from 'react-router-dom'
import {ConfigProvider} from 'antd';
import routes from './router';
import 'moment/locale/zh-cn';
import locale from 'antd/lib/locale/zh_CN';

const App = () => {
    return (
        <ConfigProvider locale={locale}>
            <Routers>
                {renderRoutes(routes)}
            </Routers>
        </ConfigProvider>
    )
}
export default App;