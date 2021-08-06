export interface Menus {
    id?: number;
    pid?: number;
    menuName?: string;
    menuIcon?: string;
    menuPath?: string;
    isShow?: boolean;
    permission?: string;
    menuNo?: number;
    parentName?: string;
    children?: Menus[];
}