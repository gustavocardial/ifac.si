import { Component } from '@angular/core';
import { Usuario } from '../../model/usuario';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  ngOnInit() {
    const inputs = document.querySelectorAll('input');
    inputs.forEach(input => {
      input.addEventListener('blur', () => {
        if (!input.value) {
          input.classList.add('invalid');
        } else {
          input.classList.remove('invalid');
        }
      });
    });
  }
  
  registro: Usuario = <Usuario>{};
  
  save() {
    console.log(this.registro.nomeUsuario + ' entrou com senha ' + this.registro.senha);
  }

}
