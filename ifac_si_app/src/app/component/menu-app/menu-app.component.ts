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
      label: 'TODAS AS PUBLICAÇÕES'
    },
    // Menu para usuários logados
    {
      caminho: '/administration/autor/new_post',
      label: 'CRIAR NOVA PUBLICAÇÃO',
      cargos: ['AUTOR', 'EDITOR']
    },
    {
      caminho: '/adminstration/meus_dados',
      label: 'MEUS DADOS',
      cargos: ['EDITOR', 'AUTOR', 'ADMIN']
    },

    {
      caminho: '/administration/editor/notification',
      label: 'REVISAR POSTS',
      cargos: ['EDITOR']
    },
    // Itens específicos para admin
    {
      caminho: '/administration/admin/viewUsers',
      label: 'GERÊNCIA DE PERFIS',
      cargos: ['ADMIN']
    }
  ];

}
