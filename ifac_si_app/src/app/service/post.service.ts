import { Injectable } from '@angular/core';
import { IService } from './I-service';
import { Post } from '../model/post';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.development';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { PageRequest } from '../model/page-request';
import { PageResponse } from '../model/page-response';

@Injectable({
  providedIn: 'root'
})
export class PostService{

  constructor(private http: HttpClient) { }

  apiUrl: string = environment.API_URL + '/post/';

  get(termoBusca?: string | undefined, pageRequest?: PageRequest, apiUrl?: string): Observable<PageResponse<Post>> {
    let url = this.apiUrl;
    if (termoBusca) {
      url += "busca/" + termoBusca;
    }
    if (pageRequest) {
      url += "?page=" + pageRequest.page + "&size=" + pageRequest.size;
      pageRequest.sort.forEach(campo => {
        url += "&sort=" + campo;
      });
    }
    return this.http.get<PageResponse<Post>>(url);
  }

  getById(id: number): Observable<Post> {
    let url = this.apiUrl + id;
    return this.http.get<Post>(url);
  }

  save(formData: FormData, id?: number): Observable<Post> {
    let url = this.apiUrl;

    // Adicionar cabeçalhos HTTP se necessário
    let headers = new HttpHeaders({
      'enctype': 'multipart/form-data'
    });

    console.log ('Testando', id);

    if (id) {
      let url = this.apiUrl + id;
      for (const pair of (formData as any).entries()) {
        console.log(pair[0], pair[1]);
      }
      
      return this.http.put<Post>(url, formData, { headers: headers });
      
    } else {
      for (const pair of (formData as any).entries()) {
        console.log(pair[0], pair[1]);
      }
      
      return this.http.post<Post>(url, formData, { headers: headers });
    }
  }

  delete(id: number): Observable<void> {
    let url = this.apiUrl + id;
    return this.http.delete<void>(url);
  }

  getByCategoria(id: number): Observable<Post[]> {
    let url = this.apiUrl + `categoria/${id}`;

    return this.http.get<Post[]>(url);
  }

  getByTag(nome: string): Observable<Post[]> {
    let url = this.apiUrl + `tag/${nome}`;

    return this.http.get<Post[]>(url);
  }

  private mapToPostRequestDTO(post: Post): any {
    return {
      titulo: post.titulo,
      usuarioId: post.usuario?.id ?? null,
      categoriaId: post.categoria?.id ?? null,
      texto: post.texto,
      legenda: post.legenda,
      status: post.EStatus ?? 'PUBLICADO',
      tags: Array.isArray(post.tags) ? post.tags.map(tag => tag.nome) : []
    };
  }
  

}
