export interface OauthClient {
    id?: number;
    clientId?: string;
    clientSecret?: string;
    authorizedGrantTypes?: string;
    scope?: string;
    webServerRedirectUri?: boolean;
    authorities?: string;
    resourceIds?: string;
    accessTokenValidity?: number;
    refreshTokenValidity?: string;
    autoapprove?:string;
    additionalInformation?:string;
}