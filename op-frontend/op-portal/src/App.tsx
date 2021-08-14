import React, {useReducer} from 'react';
import {BrowserRouter} from 'react-router-dom';
import {renderRoutes} from 'react-router-config';
import {ConfigProvider} from 'antd';
import 'moment/locale/zh-cn';
import locale from 'antd/lib/locale/zh_CN';
import routes from './router';
import {UserInfo} from "./models/UserInfo";
import userContext from './context/userContext';

const initialState: UserInfo = JSON.parse(localStorage.getItem('user-context') || '{}');

const loginReducer = (state: UserInfo, action: { type: 'loginSuccess', payload: any }) => {
    switch (action.type) {
        case 'loginSuccess':
            const newState = {...state, ...action.payload};
            localStorage.setItem('user-context', JSON.stringify(newState));
            return newState;
        default:
            return state;
    }
};

const App = () => {
    const [state, dispatch] = useReducer(loginReducer, initialState);

    return (
        <ConfigProvider locale={locale}>
            <userContext.Provider value={{
                state,
                dispatch
            }}>
                <BrowserRouter>
                    {renderRoutes(routes)}
                </BrowserRouter>
            </userContext.Provider>
        </ConfigProvider>
    )
}
export default App;
