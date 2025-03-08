import { Component, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-ordination',
  templateUrl: './ordination.component.html',
  styleUrl: './ordination.component.css'
})
export class OrdinationComponent {
  @Output() ordemSelecionada = new EventEmitter<string>();

  ordenarLista(ordem: string) {
    this.ordemSelecionada.emit(ordem);
    //   this.lista.sort((a, b) => {
    //     if (ordem === 'asc') {
    //         return a.nome.localeCompare(b.nome);
    //     } else {
    //         return b.nome.localeCompare(a.nome);
    //     }
    // });
  }

}
