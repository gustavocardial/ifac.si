import { Component } from '@angular/core';
import { Usuario } from '../../model/usuario';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  registro: Usuario = <Usuario>{};
  
  save() {
    console.log(this.registro.nomeUsuario + ' entrou com senha ' + this.registro.senha);
  }

}
