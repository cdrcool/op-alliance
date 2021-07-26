export interface OauthToken {
    accessToken: string;
    tokenType: string;
    refreshToken: string;
    expiresIn: number;
}