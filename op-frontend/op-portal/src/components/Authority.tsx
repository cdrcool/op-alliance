import React, {useContext} from "react";
import {Redirect} from "react-router-dom";
import userContext from "../context/userContext";

type AuthorityProps = {
    value: string;
    redirect?: boolean;
    children: React.ReactElement;
};

const Authority = (props: AuthorityProps) => {
    const {value, redirect = false, children} = props;

    const {state} = useContext(userContext);
    const match = (state.authorities || []).map(item => item.authority || '').some(item => item === 'SCOPE_' + value);

    return match ? children : (redirect ? <Redirect to={"/403"}/> : null);
};

export default Authority;
