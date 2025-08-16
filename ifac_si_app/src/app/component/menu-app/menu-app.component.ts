import { Component, OnDestroy, OnInit } from '@angular/core';
import { MenuItem } from '../../model/menuItem';
import { Usuario } from '../../model/usuario';
import { ECargo } from '../../model/enum/ECargo';
import { LoginService } from '../../service/login.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-menu-app',
  templateUrl: './menu-app.component.html',
  styleUrl: './menu-app.component.css'
})
export class MenuAppComponent implements OnInit, OnDestroy{

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
  
  permissoes: Map<ECargo | null, Map<string, string>> = new Map([
    [null, new Map([  // Menu para visitantes (usuário não logado)
      ['Todas as Publicações', '/view_posts'],
      ['Curso', '/course'],
      ['Artigos Científicos', '/search-projects'],
      ['<i class="bi bi-discord"></i>', 'https://discord.gg/VVMNNhFM']
    ])],
    [ECargo.autor, new Map([
      ['Todas as Publicações', '/view_posts'],
      ['Criar Nova Publicação', '/administration/autor/new_post'],
      ['Meus Dados', '/administration/my_publications']
    ])],
    [ECargo.editor, new Map([
      ['Todas as Publicações', '/view_posts'],
      ['Criar Nova Publicação', '/administration/autor/new_post'],
      ['Meus Dados', '/administration/my_publications'],
      ['Revisar Posts', '/administration/editor/notification']
    ])],
    [ECargo.admin, new Map([
      ['Todas as Publicações', '/view_posts'],
      ['Curso', '/course'],
      ['Artigos Científicos', '/search-projects'],
      ['Gerência de Perfis', '/administration/admin/viewUsers']
    ])]
  ]);

  mudarUsuario(): void {
    const cargos = [null, ...Object.values(ECargo)] as (ECargo | null)[]; // Pega os valores do enum
    const indexAtual = cargos.indexOf(this.cargoAtual);
    const proximoIndex = (indexAtual + 1) % cargos.length; // Passa para o próximo
    this.cargoAtual = cargos[proximoIndex];

  }

  usuarioTemAcesso(): { label: string, caminho: string }[] {
    const menu = this.permissoes.get(this.cargoAtual ?? null) || this.permissoes.get(null);; // Pega o menu do cargo ou o padrão (null)
    if (!menu) return [];
  
    return Array.from(menu, ([label, caminho]) => ({ label, caminho })); // Converte Map em array
  }

  logout(): void {
    this.servicoLogin.logout();
  }

  ngOnDestroy(): void {
    // Importante para evitar memory leaks
    this.subscription.unsubscribe();
  }

  //Implementar menu responsivo para celular
}
