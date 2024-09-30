import { AfterViewInit, Component, ElementRef, Renderer2, ViewChild } from '@angular/core';
import { Usuario } from '../../model/usuario';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements AfterViewInit {
  @ViewChild('enterButton', { static: false }) enterButton!: ElementRef;
  @ViewChild('senha', { static: false }) senhaField!: ElementRef;
  @ViewChild('nomeUsuario', { static: false }) nomeUsuarioField!: ElementRef;

  showPassword = false;
  registro: Usuario = <Usuario>{};

  constructor(private renderer: Renderer2) {}
  
  ngAfterViewInit() {
    this.setupInputListeners();
    this.updateButtonState();
  }

  setupInputListeners() {
    [this.nomeUsuarioField, this.senhaField].forEach(field => {
      this.renderer.listen(field.nativeElement, 'blur', () => {
        if (!field.nativeElement.value) {
          this.renderer.addClass(field.nativeElement, 'invalid');
          alert('Preencha nome de usuário e senha!');
        } else {
          this.renderer.removeClass(field.nativeElement, 'invalid');
        }
      });
      this.renderer.listen(field.nativeElement, 'input', () => {
        this.updateButtonState();
      });
    });
  }

  updateButtonState() {
    if (this.nomeUsuarioField.nativeElement.value && this.senhaField.nativeElement.value) {
      this.renderer.addClass(this.enterButton.nativeElement, 'valid');
    } else {
      this.renderer.removeClass(this.enterButton.nativeElement, 'valid');
    }
  }

  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
    this.renderer.setAttribute(
      this.senhaField.nativeElement,
      'type',
      this.showPassword ? 'text' : 'password'
    );
  }
  
  save() {
    if (this.registro.nomeUsuario && this.registro.senha) {
      console.log(this.registro.nomeUsuario + ' entrou com senha ' + this.registro.senha);
    } else {
      alert('Por favor, preencha todos os campos.');
    }
  }

}
