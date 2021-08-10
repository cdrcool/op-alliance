import React, {useContext} from "react";
import userContext from "../context/userContext";
import {TableDropdown} from "@ant-design/pro-table";
import {DropdownProps} from "@ant-design/pro-table/lib/components/Dropdown";

type AuthTableDropDownProps = {
    key: string;
} & DropdownProps;

const AuthTableDropDown = (props: AuthTableDropDownProps) => {
    const {key, menus} = props;

    const {state} = useContext(userContext);
    const authorities = (state.authorities || []).map(item => item.authority || '');
    // @ts-ignore
    const authMenus = (menus || []).filter(menu => authorities.some(item => item === 'SCOPE_' + menu.authority));

    return authMenus.length > 0 ? <TableDropdown key={key} menus={authMenus}/> : null;
};

export default AuthTableDropDown;
