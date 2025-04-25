import { Injectable } from '@angular/core';
import { IService } from './I-service';
import { Tag } from '../model/tag';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment.development';
import { Post } from '../model/post';

@Injectable({
  providedIn: 'root'
})
export class TagService implements IService<Tag>{

  constructor(private http: HttpClient) { }

  apiUrl: string = environment.API_URL + '/tag/';

  get(termoBusca?: string): Observable<Tag[]> {
    let url = this.apiUrl;
    // if (termoBusca) {
    //   url += "busca/" + termoBusca;
    // }
    return this.http.get<Tag[]>(url);
  }

  getTagByPost(id: number): Observable<Tag[]> {
    let url = this.apiUrl + `getTagsByPost/${id}`;

    return this.http.get<Tag[]>(url);
  }

  getById(id: number): Observable<Tag> {
    let url = this.apiUrl + id;
    return this.http.get<Tag>(url);
  }

  save(objeto: Tag): Observable<Tag> {
    let url = this.apiUrl;
    
    if (objeto.id) {
      return this.http.put<Tag>(url, objeto);
    } else {
      return this.http.post<Tag>(url, objeto);
    }
  }
  
  delete(id: number): Observable<void> {
    let url = this.apiUrl + id;
    return this.http.delete<void>(url);
  }

  private mapToPostRequestDTO(post: Post): any {
    return {
      titulo: post.titulo,
      usuarioId: post.usuario?.id ?? null,
      categoriaId: post.categoria?.id ?? null,
      texto: post.texto,
      legenda: post.legenda,
      status: post.status,
      tags: Array.isArray(post.tags) ? post.tags.map(tag => tag.nome) : []
    };
  }
}
