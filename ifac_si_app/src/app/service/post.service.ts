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
    throw new Error('Method not implemented.');
  }
  getById(id: number): Observable<Post> {
    throw new Error('Method not implemented.');
  }
  save(objeto: Post): Observable<Post> {
    throw new Error('Method not implemented.');
  }
  delete(id: number): Observable<void> {
    throw new Error('Method not implemented.');
  }


}
