import { Imagem } from "./imagem";

export type Profissional = {
  id: number;
  nome: string;
  descrição: string;
  perfil: Imagem;
};