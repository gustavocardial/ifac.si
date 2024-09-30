import { AfterViewInit, Component, ElementRef, ViewChild } from '@angular/core';
import { Usuario } from '../../model/usuario';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements AfterViewInit {
  @ViewChild('enterButton') enterButton!: ElementRef;
  @ViewChild('senha', { static: false }) senhaField!: ElementRef;
  
  ngAfterViewInit() {
    const inputs = document.querySelectorAll('input');
    inputs.forEach(input => {
      input.addEventListener('blur', () => {
        if (!input.value) {
          input.classList.add('invalid');
          alert('Preencha nome de usuário e senha!');
        } else {
          input.classList.remove('invalid');
        }
      });
      input.addEventListener('input', () => {
        this.updateButtonState();
      });
    });

    this.updateButtonState();

    const togglePassword = document.querySelector('.toggle-password');
    if (togglePassword) {
      togglePassword.addEventListener('click', () => {
        this.togglePasswordVisibility();
      });
    }
  }

  updateButtonState() {
    const form = document.querySelector('form');
    if (form && form.checkValidity()) {
      this.enterButton.nativeElement.classList.add('valid');
    } else if (form) {
      this.enterButton.nativeElement.classList.remove('valid');
    }
  }

  togglePasswordVisibility() {
    const senhaField = this.senhaField.nativeElement;
    const togglePassword = document.querySelector('.toggle-password');
    if (senhaField && togglePassword) {
      if (senhaField.type === 'password') {
        senhaField.type = 'text';
        togglePassword.classList.add('fa-eye-slash');
        togglePassword.classList.remove('fa-eye');
      } else {
        senhaField.type = 'password';
        togglePassword.classList.add('fa-eye');
        togglePassword.classList.remove('fa-eye-slash');
      }
    }
  }

  registro: Usuario = <Usuario>{};
  
  save() {
    console.log(this.registro.nomeUsuario + ' entrou com senha ' + this.registro.senha);
  }

}
