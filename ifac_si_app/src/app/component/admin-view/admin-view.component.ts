import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../../service/usuario.service';
import { Usuario } from '../../model/usuario';
import { AlertaService } from '../../service/alerta.service';
import { ETipoAlerta } from '../../model/e-tipo-alerta';

@Component({
  selector: 'app-admin-view',
  templateUrl: './admin-view.component.html',
  styleUrl: './admin-view.component.css'
})
export class AdminViewComponent implements OnInit{
  show: boolean = false;
  usuarios: Usuario[] = Array<Usuario>();
  selectedUsers: number[] = [];
  selectAll: boolean = false;
  editingUser: Usuario | null = null;
  tempData: Partial<Usuario> = {};

  constructor(
    private userServico: UsuarioService,
    private servicoAlerta: AlertaService,
  ) {}

  ngOnInit(): void {
    this.userServico.get().subscribe({
      next: (resposta: Usuario[]) => {
        this.usuarios = resposta;
      }
    })
  }

  showForm(): void {
    this.show = !this.show;
  }

  toggleSelectAll() {
    if (this.selectAll) {
      this.selectedUsers = this.usuarios.map(user => user.id);
    } else {
      this.selectedUsers = [];
    }
  }

  toggleSelect(userId: number) {
    const index = this.selectedUsers.indexOf(userId);
    if (index === -1) {
      this.selectedUsers.push(userId);
    } else {
      this.selectedUsers.splice(index, 1);
    }
    this.selectAll = this.selectedUsers.length === this.usuarios.length;
  }

  startEditing(user: Usuario) {
    this.editingUser = user;
    this.tempData = { ...user };
  }

  isSelected(userId: number): boolean {
    return this.selectedUsers.includes(userId);
  }

  saveEdit(user: Usuario) {
    console.log ("Usuário", user);
    const updatedUsuario: Usuario = { ...this.tempData } as Usuario;
    this.userServico.save(updatedUsuario).subscribe({
      complete: () => {
        this.servicoAlerta.enviarAlerta({
          tipo: ETipoAlerta.SUCESSO,
          mensagem: "Usuário atualizado com sucesso"
        });
      },
      error: (erro) => {
        this.servicoAlerta.enviarAlerta({
          tipo: ETipoAlerta.ERRO,
          mensagem: "Erro ao atualizar usuário"
        });
        console.error('Erro ao salvar:', erro);
      }
    })
    if (this.tempData) {

      // this.userServico.save(this.tempData)
      Object.assign(user, this.tempData);
      this.editingUser = null;
      this.tempData = {};
      console.log (user);
    }
  }

  deleteUser(id: number): void {
    this.userServico.delete(+id).subscribe({
      complete: () => {
        this.servicoAlerta.enviarAlerta({
          tipo: ETipoAlerta.SUCESSO,
          mensagem: "Usuário deletado com sucesso"
        });
      },
      error: (erro) => {
        this.servicoAlerta.enviarAlerta({
          tipo: ETipoAlerta.ERRO,
          mensagem: "Erro ao deletar usuário"
        });
        console.error('Erro ao salvar:', erro);
      }
    })
  }

  cancelEdit() {
    this.editingUser = null;
    this.tempData = {};
  }
}
