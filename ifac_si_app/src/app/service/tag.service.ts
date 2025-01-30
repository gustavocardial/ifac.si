import { Injectable } from '@angular/core';
import { IService } from './I-service';
import { tags } from '../model/tag';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class TagService implements IService<tags>{

  constructor(private http: HttpClient) { }

  apiUrl: string = environment.API_URL + '/tag/';

  get(termoBusca?: string): Observable<tags[]> {
    let url = this.apiUrl;
    // if (termoBusca) {
    //   url += "busca/" + termoBusca;
    // }
    return this.http.get<tags[]>(url);
  }

  getById(id: number): Observable<tags> {
    let url = this.apiUrl + id;
    return this.http.get<tags>(url);
  }

  save(objeto: tags): Observable<tags> {
    let url = this.apiUrl;
    
    if (objeto.id) {
      return this.http.put<tags>(url, objeto);
    } else {
      return this.http.post<tags>(url, objeto);
    }
  }
  
  delete(id: number): Observable<void> {
    let url = this.apiUrl + id;
    return this.http.delete<void>(url);
  }
}
