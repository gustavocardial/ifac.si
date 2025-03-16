import { HttpEvent, HttpHandler, HttpInterceptor, HttpInterceptorFn, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginService } from '../service/login.service';

@Injectable()
export class authInterceptor implements HttpInterceptor {
  
  constructor(private servicoLogin: LoginService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const token = sessionStorage.getItem('token');
    const isExpired = this.servicoLogin.isExpired();
    if (token && !isExpired) {
      request = request.clone({
        headers: request.headers.set(
          'Authorization',
          'Bearer ' + token)
      });
      return next.handle(request);
    }
    
    return next.handle(request);
  }

}
