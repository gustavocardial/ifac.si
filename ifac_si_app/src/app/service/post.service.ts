import { Injectable } from '@angular/core';
import { IService } from './I-service';
import { Post } from '../model/post';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.development';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PostService{

  constructor(private http: HttpClient) { }

  apiUrl: string = environment.API_URL + '/post/';

  get(termoBusca?: string): Observable<Post[]> {
    let url = this.apiUrl;
    if (termoBusca) {
      url += "busca/" + termoBusca;
    }
    return this.http.get<Post[]>(url);
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

    if (id) {
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
