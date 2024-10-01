import { Injectable } from '@angular/core';
import { IService } from './I-service';
import { Tag } from '../model/tag';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class TagService implements IService<Tag>{

  constructor(private http: HttpClient) { }

  apiUrl: string = environment.API_URL + '/tag';

  get(termoBusca?: string): Observable<Tag[]> {
    throw new Error('Method not implemented.');
  }
  getById(id: number): Observable<Tag> {
    throw new Error('Method not implemented.');
  }
  save(objeto: Tag): Observable<Tag> {
    throw new Error('Method not implemented.');
  }
  delete(id: number): Observable<void> {
    throw new Error('Method not implemented.');
  }
}
