import {ResourceAction} from "./ResourceAction";

export interface Resource {
    id?: number;
    categoryId?: number;
    resourceName?: string;
    resourcePath?: string;
    resourceDesc?: number;
    resourceNo?: number;
    actions?: ResourceAction[];
}