export interface Menus {
    id?: number;
    pid?: number;
    menuName?: string;
    menuIcon?: string;
    menuPath?: string;
    isHidden?: boolean;
    permission?: string;
    menuNo?: number;
    menuLevel?: number;
    children?: Menus[];
}