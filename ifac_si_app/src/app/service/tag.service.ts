import { Injectable } from '@angular/core';
import { IService } from './I-service';
import { tagDTO } from '../model/tagDTO';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class TagService implements IService<tagDTO>{

  constructor(private http: HttpClient) { }

  apiUrl: string = environment.API_URL + '/tag/';

  get(termoBusca?: string): Observable<tagDTO[]> {
    let url = this.apiUrl;
    if (termoBusca) {
      url += "busca/" + termoBusca;
    }
    return this.http.get<tagDTO[]>(url);
  }

  getById(id: number): Observable<tagDTO> {
    let url = this.apiUrl + id;
    return this.http.get<tagDTO>(url);
  }

  save(objeto: tagDTO): Observable<tagDTO> {
    let url = this.apiUrl;
    
    if (objeto.id) {
      return this.http.put<tagDTO>(url, objeto);
    } else {
      return this.http.post<tagDTO>(url, objeto);
    }
  }
  
  delete(id: number): Observable<void> {
    let url = this.apiUrl + id;
    return this.http.delete<void>(url);
  }
}
