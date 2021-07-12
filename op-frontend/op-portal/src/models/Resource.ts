export interface ResourceCategory {
    id?: number;
    categoryName?: string;
    categoryIcon?: string;
    categoryNo?: number;
    resources?: string[];
}

export interface Resource {
    id?: number;
    categoryId?: number;
    resourceName?: string;
    resourcePath?: string;
    resourceDesc?: number;
    resourceNo?: number;
}