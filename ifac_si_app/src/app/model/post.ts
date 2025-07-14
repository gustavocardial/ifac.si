import { Categoria } from "./categoria";
import { Imagem } from "./imagem";
import { EStatus } from "./enum/EStatus";
import { Tag } from "./tag";
import { Usuario } from "./usuario";
import { EVisibilidade } from "./enum/EVisibilidade";
import { EPublicacao } from "./enum/EPublicacao";

export type Post = {
    id: number,
    titulo: string,
    usuario?: Usuario,
    usuarioAlteraId?: Usuario,
    categoria?: Categoria,
    tags?: Tag[],
    texto: string,
    data?: string,
    legenda?: string,
    mensagemReprovacao?: string,
    imagens?: Imagem[],
    imagemCapa?: Imagem,
    status: EStatus,
    visibilidade: EVisibilidade,
    publicacao: EPublicacao
}