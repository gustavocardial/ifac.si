import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../../service/usuario.service';
import { Usuario } from '../../model/usuario';
import { AlertaService } from '../../service/alerta.service';
import { ETipoAlerta } from '../../model/enum/e-tipo-alerta';

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
  batchEditing: boolean = false;
  batchEditData: { [userId: number]: Partial<Usuario> } = {};

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

      if (this.batchEditing) {
        const user = this.usuarios.find(u => u.id === userId);
        if (user) {
          this.batchEditData[userId] = { ...user };
        }
      }
    } else {
      this.selectedUsers.splice(index, 1);

      if (this.batchEditing && this.batchEditData[userId]) {
        delete this.batchEditData[userId];
      }
    }
    this.selectAll = this.selectedUsers.length === this.usuarios.length;
  }

  startEditing(user: Usuario) {
    this.editingUser = user;
    this.tempData = { ...user };
  }

  startBatchEditing() {
    this.batchEditing = true;
    this.batchEditData = {};

    this.selectedUsers.forEach(userId => {
      const user = this.usuarios.find(u => u.id === userId);
      if (user) {
        this.batchEditData[userId] = { ...user };
      }
    });
  }

  saveBatchEdit() {
    let successCount = 0;
    let errorCount = 0;
    const totalUsers = this.selectedUsers.length;

    this.selectedUsers.forEach(userId => {
      const user = this.usuarios.find(u => u.id === userId);
      if (user) {
        const updatedUser = { ...user, ...this.batchEditData[userId] };

        console.log (updatedUser);
        
        this.userServico.save(updatedUser).subscribe({
          next: () => {
            Object.assign(user, this.batchEditData[userId]);
            successCount++;
            
            if (successCount + errorCount === totalUsers) {
              this.finalizeBatchEdit(successCount, errorCount);
            }
          },
          error: (erro) => {
            errorCount++;
            console.error('Erro ao salvar usuário:', userId, erro);
            
            if (successCount + errorCount === totalUsers) {
              this.finalizeBatchEdit(successCount, errorCount);
            }
          }
        });
      }
    });
  }

  finalizeBatchEdit(successCount: number, errorCount: number) {
    if (errorCount === 0) {
      this.servicoAlerta.enviarAlerta({
        tipo: ETipoAlerta.SUCESSO,
        mensagem: `${successCount} usuário(s) atualizado(s) com sucesso`
      });
    } else if (successCount === 0) {
      this.servicoAlerta.enviarAlerta({
        tipo: ETipoAlerta.ERRO,
        mensagem: `Erro ao atualizar todos os ${errorCount} usuário(s)`
      });
    }
    // } else {
    //   this.servicoAlerta.enviarAlerta({
    //     tipo: ETipoAlerta.AVISO,
    //     mensagem: `${successCount} usuário(s) atualizado(s), mas houve erro em ${errorCount} usuário(s)`
    //   });
    // }
    
    this.cancelBatchEdit();
  }

  cancelBatchEdit() {
    this.batchEditing = false;
    this.batchEditData = {};
  }

  // Verifica se um usuário está em modo de edição em lote
  isUserInBatchEdit(userId: number): boolean {
    return this.batchEditing && this.selectedUsers.includes(userId);
  }

  // Obtém os dados temporários de edição para um usuário específico
  getBatchEditData(userId: number): Partial<Usuario> {
    return this.batchEditData[userId] || {};
  }

  isSelected(userId: number): boolean {
    return this.selectedUsers.includes(userId);
  }

  deleteUsers(listaId: number[]) {
    console.log ("Usuários para deletar", listaId);
    listaId.forEach(id => {
      this.userServico.delete(id).subscribe({
        complete: () => {
          this.servicoAlerta.enviarAlerta({
            tipo: ETipoAlerta.SUCESSO,
            mensagem: "Usuário(s) deletado(s) com sucesso"
          })
        },
        error: (erro) => {
          this.servicoAlerta.enviarAlerta({
            tipo: ETipoAlerta.ERRO,
            mensagem: "Erro ao deletar usuário(s)"
          })
        }
      })
    });
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
