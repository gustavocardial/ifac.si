import { Component, OnInit } from '@angular/core';
import { MenuItem } from '../../model/menuItem';
import { Usuario } from '../../model/usuario';
import { ECargo } from '../../model/enum/cargoEnum';
import { LoginService } from '../../service/login.service';

@Component({
  selector: 'app-menu-app',
  templateUrl: './menu-app.component.html',
  styleUrl: './menu-app.component.css'
})
export class MenuAppComponent{
  
  constructor(private servicoLogin: LoginService) {

    this.servicoLogin.usuarioAutenticado.subscribe({
      next: (usuario: Usuario) => {
        this.usuario = usuario;
      }
    });
  }

  cargoAtual: ECargo | null = null;  
  usuario: Usuario = <Usuario>{};
  
  permissoes: Map<ECargo | null, Map<string, string>> = new Map([
    [null, new Map([  // Menu para visitantes (usuário não logado)
      ['Todas as Publicações', '/view_posts'],
      ['Curso', '/course'],
      ['<i class="bi bi-discord"></i>', 'https://discord.gg/VVMNNhFM']
    ])],
    [ECargo.autor, new Map([
      ['Todas as Publicações', '/view_posts'],
      ['Criar Nova Publicação', '/administration/autor/new_post'],
      ['Meus Dados', '/adminstration/my_publications']
    ])],
    [ECargo.editor, new Map([
      ['Todas as Publicações', '/view_posts'],
      ['Criar Nova Publicação', '/administration/autor/new_post'],
      ['Meus Dados', '/adminstration/my_publications'],
      ['Revisar Posts', '/administration/editor/notification']
    ])],
    [ECargo.admin, new Map([
      ['Todas as Publicações', '/view_posts'],
      ['Meus Dados', '/adminstration/my_publications'],
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
    const menu = this.permissoes.get(this.cargoAtual ?? null); // Pega o menu do cargo ou o padrão (null)
    if (!menu) return [];
  
    return Array.from(menu, ([label, caminho]) => ({ label, caminho })); // Converte Map em array
  }

  logout(): void {
    this.servicoLogin.logout();
  }

  //Implementar menu responsivo para celular
}
