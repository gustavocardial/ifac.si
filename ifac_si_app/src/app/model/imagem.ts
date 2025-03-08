import { Post } from "./post"

export type Imagem = {
    id: number,
    url: string,
    nomeArquivo: string,
    tamanho: number,
    dataUpload: Date,
    post: Post
}