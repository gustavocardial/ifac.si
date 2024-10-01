import { Injectable } from '@angular/core';
import { IService } from './I-service';
import { Categoria } from '../model/categoria';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class CategoriaService implements IService<Categoria>{

  constructor(private http: HttpClient) { }

  apiUrl: string = environment.API_URL + '/categoria';

  get(termoBusca?: string): Observable<Categoria[]> {
    let url = this.apiUrl;
    if (termoBusca) {
      url += "busca/" + termoBusca;
    }
    return this.http.get<Categoria[]>(url);
  }

  getById(id: number): Observable<Categoria> {
    let url = this.apiUrl + id;
    return this.http.get<Categoria>(url);
  }

  save(objeto: Categoria): Observable<Categoria> {
    let url = this.apiUrl;
    
    if (objeto.id) {
      return this.http.put<Categoria>(url, objeto);
    } else {
      return this.http.post<Categoria>(url, objeto);
    }
  }

  delete(id: number): Observable<void> {
    let url = this.apiUrl + id;
    return this.http.delete<void>(url);
  }
}
