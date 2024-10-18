import { Categoria } from "./categoria"
import { Imagem } from "./imagem"
import { Tag } from "./tag"
import { Usuario } from "./usuario"

export type Post = {
    id: number,
    titulo: string,
    usuario: Usuario,
    categoria: Categoria,
    tags: Tag[],
    texto: string,
    data?: Date,
    legenda: string,
    imagem: Imagem[]
}