export interface ITags {
    id: number | null; // Alterado para permitir valor null
    nome: string;
    tempId?: number; // Adicionado novo campo
}