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

  apiUrl: string = environment.API_URL + '/config/usuario/';
  
  get(termoBusca?: string): Observable<Usuario[]> {
    throw new Error('Method not implemented.');
  }
  getById(id: number): Observable<Usuario> {
    throw new Error('Method not implemented.');
  }
  save(objeto: Usuario): Observable<Usuario> {
    throw new Error('Method not implemented.');
  }
  delete(id: number): Observable<void> {
    throw new Error('Method not implemented.');
  }
}
