export interface Organization {
    id?: number;
    pid?: string;
    orgName?: string;
    orgCode?: string;
    orgCodeLink?: number;
    orgType?: number;
    parentName?: string;
    children?: Organization[];
}