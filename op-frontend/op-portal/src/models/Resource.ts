import {ResourceAction} from "./ResourceAction";

export interface Resource {
    id?: number;
    categoryId?: number;
    resourceName?: string;
    resourceEnName?: string;
    resourcePath?: string;
    resourceDesc?: number;
    resourceNo?: number;
    actions?: ResourceAction[];
}