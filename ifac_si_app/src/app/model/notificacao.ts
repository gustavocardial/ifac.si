import { Acao } from "./acao";
import { Usuario } from "./usuario";

export type Notificacao = {
  id: number;
  acao: Acao;
  usuario?: Usuario;
  lida: boolean;
};