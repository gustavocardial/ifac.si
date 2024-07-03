import { Post } from "./Post"

export type Tag = {
    id: number,
    nome: string,
    posts: Post
}