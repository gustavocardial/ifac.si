import { Post } from "./post"

export type Tag = {
    id: number,
    nome: string,
    posts: Post[]
}