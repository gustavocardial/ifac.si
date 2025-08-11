import { Component } from '@angular/core';
import { Subscription } from 'rxjs';
import { LoginService } from '../../service/login.service';
import { ECargo } from '../../model/enum/ECargo';
import { Usuario } from '../../model/usuario';

@Component({
  selector: 'app-search-projects',
  templateUrl: './search-projects.component.html',
  styleUrl: './search-projects.component.css'
})
export class SearchProjectsComponent {

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
  // pesquisa.component.ts
publicacoes = [
  {
    titulo: 'Sistema Web para Divulgação Científica',
    autores: ['Flora França', 'Beatriz Paiva'],
    tipo: 'TCC',
    data: '2025-07',
    resumo: 'Este trabalho apresenta um sistema de apoio à divulgação científica...',
    link: 'https://repositorio.instituicao.edu.br/tcc123.pdf'
  },
  {
    titulo: 'Aplicativo para Inclusão Digital de Idosos',
    autores: ['Ana Costa'],
    tipo: 'Artigo',
    data: '2024-11',
    resumo: 'Este artigo analisa a usabilidade de apps voltados para a terceira idade...',
    link: 'https://revista.instituicao.edu.br/artigo567.pdf'
  }
];

}
