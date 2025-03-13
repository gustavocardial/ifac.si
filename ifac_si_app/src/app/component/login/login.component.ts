import { AfterViewInit, Component, ElementRef, Renderer2, ViewChild } from '@angular/core';
import { Usuario } from '../../model/usuario';
import { LoginService } from '../../service/login.service';
import { AlertaService } from '../../service/alerta.service';
import { ETipoAlerta } from '../../model/e-tipo-alerta';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements AfterViewInit {
  @ViewChild('enterButton', { static: false }) enterButton!: ElementRef;
  @ViewChild('senhaField', { static: false }) senhaField!: ElementRef;
  @ViewChild('nomeUsuarioField', { static: false }) nomeUsuarioField!: ElementRef;

  showPassword = false;
  registro: Usuario = <Usuario>{};

  constructor(
    private renderer: Renderer2,
    private servicoLogin: LoginService,
    private servicoAlerta: AlertaService,
    private router: Router,
    private route: ActivatedRoute,) {}
  
  ngAfterViewInit() {
    if (this.nomeUsuarioField && this.senhaField) {
      // console.log(this.nomeUsuarioField);  // Verificar se o elemento está acessível
      // console.log(this.senhaField);

      this.setupInputListeners();
      this.updateButtonState();
    } 
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
    // this.renderer.setAttribute(
    //   this.senhaField.nativeElement,
    //   'type',
    //   this.showPassword ? 'text' : 'password'
    // );
  }
  
  login() {
    if (this.registro.nomeUsuario && this.registro.senha) {
      this.servicoLogin.login(this.registro).subscribe({
        complete: () => {
          this.servicoAlerta.enviarAlerta({
            tipo: ETipoAlerta.SUCESSO,
            mensagem: "Login realizado com sucesso"
          });
          this.router.navigate(['/view_posts']);
        },
        error: (erro) => {
          this.servicoAlerta.enviarAlerta({
            tipo: ETipoAlerta.ERRO,
            mensagem: "Erro ao salvar o realizar login"
          });
        }
      })
    } else {
      alert('Por favor, preencha todos os campos.');
    }
  }

}
