import { Injectable } from '@angular/core';
import { IService } from './I-service';
import { Usuario } from '../model/usuario';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService implements IService<Usuario> {

  constructor(private http: HttpClient) { }

  apiUrl: string = environment.API_URL + '/usuario/';
  
  get(termoBusca?: string): Observable<Usuario[]> {
    let url = this.apiUrl;
    if (termoBusca) {
      url += "busca/" + termoBusca;
    }
    return this.http.get<Usuario[]>(url);
  }

  getById(id: number): Observable<Usuario> {
    let url = this.apiUrl + id;
    return this.http.get<Usuario>(url);
  }

  save(objeto: Usuario): Observable<Usuario> {
    let url = this.apiUrl;
    
    if (objeto.id) {
      return this.http.put<Usuario>(url, objeto);
    } else {
      return this.http.post<Usuario>(url, objeto);
    }
  }

  delete(id: number): Observable<void> {
    let url = this.apiUrl + id;
    return this.http.delete<void>(url);
  }
}
