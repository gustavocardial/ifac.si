import { Injectable } from '@angular/core';
import { IService } from './I-service';
import { Post } from '../model/post';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.development';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PostService implements IService<Post>{

  constructor(private http: HttpClient) { }

  apiUrl: string = environment.API_URL + '/post';

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
    
    if (objeto.id) {
      return this.http.put<Post>(url, objeto);
    } else {
      return this.http.post<Post>(url, objeto);
    }
  }

  delete(id: number): Observable<void> {
    let url = this.apiUrl + id;
    return this.http.delete<void>(url);
  }


}
