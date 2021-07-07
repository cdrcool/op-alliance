export interface PageResult<T> {
    content: T[];
    number: number;
    size: number;
    totalElements: number;
}