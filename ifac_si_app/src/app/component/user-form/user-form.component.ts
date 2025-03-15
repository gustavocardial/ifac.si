import { AfterViewInit, Component, ElementRef, EventEmitter, Output, Renderer2, ViewChild } from '@angular/core';
import { UsuarioService } from '../../service/usuario.service';
import { AlertaService } from '../../service/alerta.service';
import { ETipoAlerta } from '../../model/e-tipo-alerta';
import { ActivatedRoute, Router } from '@angular/router';
import { Usuario } from '../../model/usuario';
import { LoginService } from '../../service/login.service';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrl: './user-form.component.css'
})
export class UserFormComponent implements AfterViewInit{
  constructor(
    private renderer: Renderer2,
    private servicoUsuario: UsuarioService,
    private servicoAlerta: AlertaService,
    private servicoLogin: LoginService,
    private router: Router,
    private route: ActivatedRoute,) {}

  @Output() cancelForm = new EventEmitter;
  @ViewChild('cancelButton') cancelButton!: ElementRef;
  @ViewChild('senha') senhaInput!: ElementRef;
  @ViewChild('confirmarSenha') confirmarSenhaInput!: ElementRef;

  usuario: Usuario = <Usuario>{};
  showPasswordPrimary: boolean = false;
  showPasswordConfirm: boolean = false;

  onSubmit(form: any): void {

    if (this.senhaInput.nativeElement.value !== this.confirmarSenhaInput.nativeElement.value) {
      console.log(this.senhaInput.nativeElement.value, this.confirmarSenhaInput.nativeElement.value);
      alert('As senhas não coincidem!');
      return;
    }

    this.servicoLogin.register(this.usuario).subscribe({
       complete: () => {
         this.servicoAlerta.enviarAlerta({
           tipo: ETipoAlerta.SUCESSO,
           mensagem: "Usuário salvo com sucesso"
         });
       },
       error: (erro) => {
         this.servicoAlerta.enviarAlerta({
           tipo: ETipoAlerta.ERRO,
           mensagem: "Erro ao salvar o usuário"
         });
         console.error('Erro ao salvar:', erro);
       }
    })
  }

  togglePasswordVisibilityPrimary() {
    this.showPasswordPrimary = !this.showPasswordPrimary;
  }
  
  togglePasswordVisibilityConfirm() {
    this.showPasswordConfirm = !this.showPasswordConfirm;
  }

  // togglePasswordVisibility() {
  //   this.showPassword = !this.showPassword;
  //   this.renderer.setAttribute(
  //     this.senhaInput.nativeElement,
  //     'type',
  //     this.showPassword ? 'text' : 'password'
  //   );
  //   this.renderer.setAttribute(
  //     this.confirmarSenhaInput.nativeElement,
  //     'type',
  //     this.showPassword ? 'text' : 'password'
  //   );
  // }

  onCancel(): void {
    console.log('Cancelado!');
  }


  ngAfterViewInit() {
    if (this.cancelButton && this.cancelButton.nativeElement) {
      this.renderer.listen(this.cancelButton.nativeElement, 'click', () => {
        this.cancelForm.emit();
      });
    }
  }
}
