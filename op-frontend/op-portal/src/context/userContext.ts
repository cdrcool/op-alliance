import React from "react";
import {User} from "../models/User";

const userContext = React.createContext<{name: string}>({name: ''});

export default userContext;
