import { Categoria } from "./categoria";
import { Imagem } from "./imagem";
import { statusPost } from "./statusEnum";
import { tags } from "./tag";
import { Usuario } from "./usuario";

export type Post = {
    id: number,
    titulo: string,
    usuario?: Usuario,
    categoria?: Categoria,
    tags?: tags[],
    texto: string,
    data?: string,
    legenda?: string,
    imagens?: Imagem[],
    imagemCapa?: Imagem,
    EStatus: statusPost
}