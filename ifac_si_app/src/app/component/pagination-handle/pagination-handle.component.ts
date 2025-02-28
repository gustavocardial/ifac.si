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
    return Array.from(Array(this.totalPaginas).keys());
  }

  mudarPagina(pagina: number) {
    this.paginaSelecionada.emit(pagina);
  }
}
