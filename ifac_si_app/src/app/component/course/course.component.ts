import { Component, OnDestroy, OnInit } from '@angular/core';
import { CorpoDocente } from '../../model/corpoDocente';
import { LoginService } from '../../service/login.service';
import { ECargo } from '../../model/enum/ECargo';
import { Usuario } from '../../model/usuario';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrl: './course.component.css'
})
export class CourseComponent implements OnInit, OnDestroy{

  cargoAtual: ECargo | null = null;  
  usuario: Usuario = <Usuario>{};
  private subscription: Subscription = new Subscription();
    
  constructor(private servicoLogin: LoginService) {}

  ngOnInit(): void {
  // Inscreva-se no BehaviorSubject durante o ciclo de vida do componente
  this.subscription = this.servicoLogin.usuarioAutenticado.subscribe({
    next: (usuario: Usuario) => {
      // console.log("MenuAppComponent recebeu atualização de usuário:", JSON.stringify(usuario));
      this.usuario = usuario;


      console.log (usuario)
      // Log para depuração
      // console.log("Usuário logado: ", this.usuario);
      // console.log("Cargo recebido: ", this.usuario.cargo);
      
      // Tratamento mais robusto para converter a string do cargo para o enum ECargo
      if (usuario?.cargo) {
        try {
          // Método 1: Conversão direta (se a string for exatamente igual ao nome do enum)
          this.cargoAtual = ECargo[usuario.cargo as keyof typeof ECargo];
            
          // Método 2: Se o método 1 falhar, tente encontrar o valor que corresponde
          if (this.cargoAtual === undefined) {
            this.cargoAtual = Object.values(ECargo).find(
              cargo => cargo.toString() === usuario.cargo
            ) as ECargo || null;
          }
            
          // console.log("Cargo convertido para enum:", this.cargoAtual);
        } catch (error) {
          // console.error("Erro ao converter cargo:", error);
          this.cargoAtual = null;
        }
      } else {
        // console.log("Nenhum cargo definido no usuário");
        this.cargoAtual = null;
      }
    },
      error: (error) => {
        console.error("Erro ao receber usuário:", error);
      }
    });
  }

  ngOnDestroy(): void {
    // Importante para evitar memory leaks
    this.subscription.unsubscribe();
  }

  docentes: CorpoDocente[] = [
    {
      nome: 'Gustavo Cardial',
      nivel: 'Mestre',
      cargo: 'Coordenador e professor'
    },
    {
      nome: 'Flávio Miranda',
      nivel: 'Mestre e doutor',
      cargo: 'Professor'
    },
    {
      nome: 'Marlon',
      nivel: 'Mestre e doutor',
      cargo: 'Professor'
    },
    {
      nome: 'Darueck',
      nivel: 'Mestre',
      cargo: 'Professor'
    },
    {
      nome: 'Silvana',
      nivel: 'Mestre',
      cargo: 'Professora'
    },
    {
      nome: 'Breno Silveira',
      nivel: 'Mestre',
      cargo: 'Professor'
    }
    // {
    //   nome: 'Marlon '
    // }
  ]


}
