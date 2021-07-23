import React, {FC, useEffect} from 'react';
import {useHistory} from "react-router-dom";
import {renderRoutes} from "react-router-config";

const SecurityLayout: FC = (props) => {

    const history = useHistory();

    useEffect(() => {
        const user = window.sessionStorage.getItem("user");
        if (!user) {
            history.replace('/login');
        }
    }, []);

    return (
        <>
            {
                // @ts-ignore
                renderRoutes(props.route.routes)
            }
        </>
    );
}

export default SecurityLayout;