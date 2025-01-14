import { Categoria } from "./categoria"
import { Imagem } from "./imagem"
import { tagDTO } from "./tagDTO"
import { Usuario } from "./usuario"

export type Post = {
    id: number,
    titulo: string,
    usuario: Usuario,
    categoria?: Categoria,
    tags: tagDTO[],
    texto: string,
    data?: string,
    legenda: string,
    imagem: Imagem[]
}