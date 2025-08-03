import { Injectable } from '@angular/core';
import { IService } from './I-service';
import { Post } from '../model/post';
import { catchError, Observable, tap, throwError } from 'rxjs';
import { environment } from '../../environments/environment.development';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
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

  reprovarPost(id: number, mensagem?: string): Observable<Post> {
    const url = this.apiUrl + `reprovacao/${id}`;

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    });

    const body = { mensagem: mensagem || '' };
  
    console.log('=== DEBUG REPROVAR POST ===');
    console.log('URL completa:', url);
    console.log('ID:', id);
    console.log('Mensagem:', mensagem);
    console.log('Body:', body);
    console.log('API URL base:', this.apiUrl);
    
    return this.http.put<Post>(url, body, {headers}).pipe(
      tap(response => console.log('Sucesso:', response)),
      catchError(error => {
        console.error('Erro na requisição:', error);
        console.error('Status:', error.status);
        console.error('Message:', error.message);
        console.error('URL que falhou:', error.url);
        return throwError(error);
      })
    );
  }

  corrigirPost(id: number, formData: FormData): Observable<Post> {
    const url = this.apiUrl + `correcao/${id}`;
    const headers = new HttpHeaders({ 'enctype': 'multipart/form-data' });

    return this.http.put<Post>(url, formData, { headers });
  }

  // private mapToPostRequestDTO(post: Post): any {
  //   return {
  //     titulo: post.titulo,
  //     usuarioId: post.usuario?.id ?? null,
  //     categoriaId: post.categoria?.id ?? null,
  //     texto: post.texto,
  //     legenda: post.legenda,
  //     status: post.status ?? 'PUBLICADO',
  //     tags: Array.isArray(post.tags) ? post.tags.map(tag => tag.nome) : []
  //   };
  // }
  

}
