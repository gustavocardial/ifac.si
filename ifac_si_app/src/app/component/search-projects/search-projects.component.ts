import { Component } from '@angular/core';

@Component({
  selector: 'app-search-projects',
  templateUrl: './search-projects.component.html',
  styleUrl: './search-projects.component.css'
})
export class SearchProjectsComponent {
  // pesquisa.component.ts
publicacoes = [
  {
    titulo: 'Sistema Web para Divulgação Científica',
    autores: ['Flora França', 'Beatriz Paiva'],
    tipo: 'TCC',
    data: '2025-07',
    resumo: 'Este trabalho apresenta um sistema de apoio à divulgação científica...',
    link: 'https://repositorio.instituicao.edu.br/tcc123.pdf'
  },
  {
    titulo: 'Aplicativo para Inclusão Digital de Idosos',
    autores: ['Ana Costa'],
    tipo: 'Artigo',
    data: '2024-11',
    resumo: 'Este artigo analisa a usabilidade de apps voltados para a terceira idade...',
    link: 'https://revista.instituicao.edu.br/artigo567.pdf'
  }
];

}
