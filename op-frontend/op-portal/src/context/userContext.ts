import React from "react";
import {UserInfo} from "../models/UserInfo";

interface Context {
    state: UserInfo,
    dispatch: any,
}

const userContext = React.createContext<Context>({state: {}, dispatch: null});

export default userContext;
