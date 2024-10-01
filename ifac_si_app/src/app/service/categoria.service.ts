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
    throw new Error('Method not implemented.');
  }
  getById(id: number): Observable<Categoria> {
    throw new Error('Method not implemented.');
  }
  save(objeto: Categoria): Observable<Categoria> {
    throw new Error('Method not implemented.');
  }
  delete(id: number): Observable<void> {
    throw new Error('Method not implemented.');
  }
}
