import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../service/usuario.service';
import { Usuario } from '../model/usuario';

@Component({
  selector: 'app-admin-view',
  templateUrl: './admin-view.component.html',
  styleUrl: './admin-view.component.css'
})
export class AdminViewComponent implements OnInit{
  usuarios: Usuario[] = Array<Usuario>();
  constructor(private userServico: UsuarioService) {}

  ngOnInit(): void {
    this.userServico.get().subscribe({
      next: (resposta: Usuario[]) => {
        this.usuarios = resposta;
      }
    })
  }


}
