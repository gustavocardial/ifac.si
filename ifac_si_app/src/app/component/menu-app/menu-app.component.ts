import { Component } from '@angular/core';
import { MenuItem } from '../../model/menuItem';

@Component({
  selector: 'app-menu-app',
  templateUrl: './menu-app.component.html',
  styleUrl: './menu-app.component.css'
})
export class MenuAppComponent {
  private menuItems: MenuItem[] = [
    // Menu público (sem login)
    {
      caminho: '/view_posts',
      label: 'Todas as publicações'
    },
    // Menu para usuários logados
    {
      caminho: '/administration/autor/new_post',
      label: 'Criar nova publicação',
      cargos: ['AUTOR', 'ADMIN']
    },
    {
      caminho: '/meus-dados',
      label: 'Meus dados',
      cargos: ['USUARIO', 'AUTOR', 'ADMIN']
    },
    // Itens específicos para admin
    {
      caminho: '/admin/dashboard',
      label: 'Dashboard',
      cargos: ['ADMIN']
    }
  ];

}
