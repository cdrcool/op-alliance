export interface Organization {
    id?: number;
    pid?: number;
    orgName?: string;
    orgCode?: string;
    orgCodeLink?: number;
    orgType?: number;
    parentName?: string;
    children?: Organization[];
}