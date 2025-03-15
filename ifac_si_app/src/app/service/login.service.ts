import { Injectable } from '@angular/core';
import { Usuario } from '../model/usuario';
import { BehaviorSubject, tap } from 'rxjs';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment.development';
import { TokenResponse } from '../model/toke-response';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(
    private http: HttpClient,
    private router: Router
  ) {
    this.usuario = JSON.parse(sessionStorage.getItem('usuario') || '{}');
    this.usuarioAutenticado.next(this.usuario);
  }

  private usuario: Usuario = <Usuario>{};
  usuarioAutenticado: BehaviorSubject<Usuario> = new BehaviorSubject<Usuario>(this.usuario);

  login(credenciais: Usuario) {
    let url = environment.API_URL + '/auth/login';

    return this.http.post<TokenResponse>(url, credenciais).pipe(
      tap((resposta: TokenResponse) => {
        const token = resposta.token;
        const decodedToken = JSON.parse(atob(token.split('.')[1]));
        const tokenExpiration = decodedToken.exp * 1000;

        console.log("Resposta completa:", resposta);
        console.log("Token decodificado:", decodedToken);
        
        const usuarioAutenticado: Usuario = {
          id: resposta.id ?? 0,
          nomeUsuario: resposta.nomeUsuario || decodedToken.sub,
          email: resposta.email ?? "",
          cargo: resposta.cargo || decodedToken.cargo
        };

        sessionStorage.setItem('token', token);
        sessionStorage.setItem('usuario', JSON.stringify(usuarioAutenticado));
        sessionStorage.setItem('tokenExpiration', tokenExpiration.toString());
        
        this.usuario = usuarioAutenticado;
        this.usuarioAutenticado.next(usuarioAutenticado);
      })
      // complete: () => {
      //   this.router.navigate(['/view-posts']);
      // }
    );
  }

  logout(): void {
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('usuario');
    sessionStorage.removeItem('tokenExpiration');
    this.router.navigate(['/login']);
  }

  isExpired(): boolean {
    const expiration = sessionStorage.getItem('tokenExpiration');
    const expirationDate = new Date(Number(expiration));
    return expirationDate < new Date();
  }

}
