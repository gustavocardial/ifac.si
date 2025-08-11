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

    if (id) {
      let url = this.apiUrl + id;
      return this.http.put<Post>(url, formData, { headers: headers });
    } else {      
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
    
    return this.http.put<Post>(url, body, {headers}).pipe(
      tap(response => console.log('Sucesso:', response)),
      catchError(error => {
        return throwError(error);
      })
    );
  }

  corrigirPost(id: number, formData: FormData): Observable<Post> {
    const url = this.apiUrl + `correcao/${id}`;
    const headers = new HttpHeaders({ 'enctype': 'multipart/form-data' });

    return this.http.put<Post>(url, formData, { headers });
  }
  
  // 📝 Salvar rascunho baseado em um post existente
  salvarRascunho(postId: number, formData: FormData): Observable<Post> {
    const url = `${this.apiUrl}${postId}/rascunho`;
    const headers = new HttpHeaders({ 'enctype': 'multipart/form-data' });

    return this.http.post<Post>(url, formData, { headers });
  }

  // 🚀 Publicar rascunho sem atualizações (JSON puro)
  publicarRascunho(rascunhoId: number, objeto: any): Observable<Post> {
    const url = `${this.apiUrl}${rascunhoId}/publicar`;
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    return this.http.post<Post>(url, objeto, { headers });
  }

  // 🚀 Publicar rascunho com atualizações (formData)
  publicarRascunhoComAtualizacoes(rascunhoId: number, formData: FormData): Observable<Post> {
    const url = `${this.apiUrl}${rascunhoId}/publicar`;
    const headers = new HttpHeaders({ 'enctype': 'multipart/form-data' });

    return this.http.post<Post>(url, formData, { headers });
  }

  // 👤 Buscar todos os rascunhos de um usuário
  getRascunhosPorUsuario(usuarioId: number): Observable<Post[]> {
    const url = `${this.apiUrl}rascunhos/usuario/${usuarioId}`;
    return this.http.get<Post[]>(url);
  }

  // 🔍 Buscar rascunho associado a um post
  getRascunhoDePost(postId: number): Observable<Post | null> {
    const url = `${this.apiUrl}${postId}/rascunho`;
    return this.http.get<Post>(url);
  }

  // 🗑️ Descartar rascunho permanentemente
  descartarRascunho(rascunhoId: number): Observable<void> {
    const url = `${this.apiUrl}rascunhos/${rascunhoId}`;
    return this.http.delete<void>(url);
  }


}
