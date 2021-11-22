export interface LoginResponse {
    username: string;
    authenticationToken: string;
    refreshToken: string;
    expiresAt: Date;
}