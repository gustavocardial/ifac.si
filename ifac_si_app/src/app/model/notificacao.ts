import { ETipoAcao } from "./enum/e-tipo-acao";
import { Post } from "./post";
import { Usuario } from "./usuario";

export type Notificacao = {
  id: number;
  usuario?: Usuario;
  post: Post;
  tipoAcao: ETipoAcao;
  dataHora: string; // ISO string (ex: 2025-04-08T15:30:00)
  lida: boolean;
};