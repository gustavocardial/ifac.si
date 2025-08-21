import { ETipoAcao } from "./enum/e-tipo-acao";
import { Post } from "./post";
import { Usuario } from "./usuario";

export type Acao = {
  id: number;
  usuario?: Usuario;
  post: Post;
  dataHora: string; // ISO string (ex: 2025-04-08T15:30:00)
  tipoAcao: ETipoAcao;
};