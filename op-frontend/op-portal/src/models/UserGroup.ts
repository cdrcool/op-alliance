import {User} from "./User";

export interface UserGroup {
    id?: number;
    groupName?: number;
    groupDesc?: string;
    groupNo?: string;
    users?: User[];
}