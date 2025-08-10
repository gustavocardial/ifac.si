import { Imagem } from "./imagem";

export type Notificacao = {
  id: number;
  nome: string;
  descrição: string;
  imagem: Imagem;
};