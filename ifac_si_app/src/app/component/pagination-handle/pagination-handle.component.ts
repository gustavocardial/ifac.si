import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-pagination-handle',
  templateUrl: './pagination-handle.component.html',
  styleUrl: './pagination-handle.component.css'
})
export class PaginationHandleComponent {
  @Input() totalItens: number = 0;
  @Input() totalPaginas: number = 0;
  @Input() paginaAtual: number = 0;
  @Input() tamanhoPagina: number = 0;
  @Output() paginaSelecionada = new EventEmitter<number>();

  listarPaginas(): number[] {
    if (this.paginaAtual != undefined && this.totalPaginas != undefined) {
      let inicio = Math.max(0, Math.min(this.paginaAtual - 2, this.totalPaginas - 5));
      let fim = Math.min(this.totalPaginas, inicio + 5);
      return Array.from(Array(fim - inicio).keys()).map(i => inicio + i);
    }
    return [];
  }

  mudarPagina(pagina: number) {
    this.paginaSelecionada.emit(pagina);
  }
}
