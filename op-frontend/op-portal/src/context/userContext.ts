import React from "react";
import {User} from "../models/User";

const userContext = React.createContext<User>({});

export default userContext;
