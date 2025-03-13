export interface TokenResponse {
    token: string;
    tipo: string;
    id?: number;
    nomeUsuario?: string; 
    email?: string;
    cargo?: string;
}