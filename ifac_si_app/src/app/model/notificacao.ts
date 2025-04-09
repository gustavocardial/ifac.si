import { TipoAcao } from "./enum/tipoAcaoEnum";
import { Post } from "./post";
import { Usuario } from "./usuario";

export type Notificacao = {
    id: number;
    usuario: Usuario;
    post: Post;
    tipoAcao: TipoAcao;
    dataHora: string; // ISO string (ex: 2025-04-08T15:30:00)
    lida: boolean;
  };