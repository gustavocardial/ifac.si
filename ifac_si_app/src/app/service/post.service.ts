import { Injectable } from '@angular/core';
import { IService } from './I-service';
import { Post } from '../model/post';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.development';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PostService implements IService<Post>{

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

  save(objeto: Post): Observable<Post> {
    let url = this.apiUrl;
    let formData = new FormData();

    // Converter os dados do post para JSON e adicioná-los ao FormData  
    let postJson = JSON.stringify(this.mapToPostRequestDTO(objeto));
    formData.append('post', new Blob([postJson], { type: 'application/json' }));

    // Adicionar um "arquivo vazio" para manter a estrutura multipart/form-data
    formData.append('imagemCapa', new Blob([], { type: 'image/png' }), 'empty.png');
    formData.append('file', new Blob([], { type: 'image/png' }), 'empty.png'); // Caso necessite enviar um arquivo vazio também

    // Adicionar cabeçalhos HTTP se necessário
    let headers = new HttpHeaders({
      'enctype': 'multipart/form-data'
    });

    if (objeto.id) {
      return this.http.put<Post>(url, formData, { headers: headers });
    } else {
      console.log (formData);

      return this.http.post<Post>(url, formData, { headers: headers });
    }
  }

  delete(id: number): Observable<void> {
    let url = this.apiUrl + id;
    return this.http.delete<void>(url);
  }

  private mapToPostRequestDTO(post: Post): any {
    return {
      titulo: post.titulo,
      usuarioId: post.usuario?.id,
      categoriaId: post.categoria?.id,
      texto: post.texto,
      legenda: post.legenda,
      status: post.EStatus,
      tags: Array.isArray(post.tags) ? post.tags.map(tag => tag.nome) : []
    };
  }
  

}
