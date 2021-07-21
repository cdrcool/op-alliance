import {Resource} from "./Resource";

export interface ResourceCategory {
    id?: number;
    categoryName?: string;
    categoryEnName?: string;
    categoryIcon?: string;
    serverName?: string;
    categoryNo?: number;
    resourceNames?: string[];
    resources?: Resource[];
}