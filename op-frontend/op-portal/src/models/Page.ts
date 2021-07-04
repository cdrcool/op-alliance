export interface Page<T> {
    content: T[];
    number: number;
    size: number;
    totalElements: number;
}

const defaultPage = {
    content: [],
    number: 0,
    size: 10,
    totalElements: 0,
}

export default defaultPage;