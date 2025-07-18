import { Component, Renderer2 } from '@angular/core';
import { PageRequest } from '../../model/page-request';
import { PageResponse } from '../../model/page-response';
import { Post } from '../../model/post';
import { LoginService } from '../../service/login.service';
import { PostService } from '../../service/post.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-trash-view',
  templateUrl: './trash-view.component.html',
  styleUrl: './trash-view.component.css'
})
export class TrashViewComponent {
  paginaRequisicao: PageRequest = new PageRequest();
  paginaResposta: PageResponse<Post> = <PageResponse<Post>>{};
  termoBusca: string = '';
  selectedCategories: Post[] = Array<Post>(); // Lista de categorias selecionadas
  selectedTags: Post[] = Array<Post>(); // Lista de tags selecionadas
  ordenacao: string = 'asc';

  constructor(
    private servicoLogin: LoginService, 
    private servicoPost: PostService,
    private renderer: Renderer2,
    private router: Router,) {}

  lista_teste = [
    {
      id: 1,
      acao: "REMOCAO",
      dataAcao: "2025-07-17T14:32:00",
      usuarioResponsavelId: 12,
      motivo: "Postagem duplicada",
      post: {
        id: 55,
        titulo: "Semana de Tecnologia IFAC",
        texto: "Confira a programação completa da semana de tecnologia...",
        usuario: {
          id: 8,
          email: "autor@ifac.edu.br",
          nomeUsuario: "autor.tecnologia",
          senha: "**********",
          cargo: "AUTOR",
          ativo: true
        },
        categoria: {
          id: 3,
          nome: "Eventos"
        },
        tags: [
          { id: 1, nome: "Tecnologia" },
          { id: 2, nome: "Educação" }
        ],
        data: "2025-06-25T09:00:00",
        legenda: "Programação completa do evento",
        mensagemReprovacao: null,
        imagens: [
          {
            id: 201,
            url: "https://site.com/imagens/evento.png",
            descricao: "Banner do evento"
          }
        ],
        imagemCapa: {
          id: 201,
          url: "https://site.com/imagens/evento.png",
          descricao: "Banner do evento"
        },
        status: "REMOVIDO",
        visibilidade: "PRIVADO",
        publicacao: "NAO_PUBLICADO"
      }
    }
  ];

  getDiasRestantes(dataAcao: string): number {
    const dataRemocao = new Date(dataAcao);
    const dataLimite = new Date(dataRemocao);
    dataLimite.setDate(dataRemocao.getDate() + 30); // adiciona 30 dias

    const hoje = new Date();
    const diff = dataLimite.getTime() - hoje.getTime(); // diferença em ms

    const diasRestantes = Math.ceil(diff / (1000 * 60 * 60 * 24)); // converte ms em dias
    return diasRestantes > 0 ? diasRestantes : 0; // evita número negativo
  }

    // getCategoria(id: number | null): void {
    //   if (id === null) {
    //     // Lógica para quando não há categoria selecionada
    //     this.selectedCategories = [];
    //     this.applyFilters();  // Exemplo: limpa os posts se não houver categoria
    //   } else {
    //     this.servicoPost.getByCategoria(id).subscribe({
    //       next: (resposta: Post[]) => {
    //         this.selectedCategories = resposta;
    //         this.applyFilters();
    //       }
    //     })
    //   }
  
    //   // this.applyFilters();
    // }
  
    // getByTag(nome: string | null): void {
    //   if (nome === null || nome === '') {
    //     // Lógica para quando não há tag selecionada
    //     this.selectedTags = [];
    //     this.applyFilters();
    //   } else {
    //     this.servicoPost.getByTag(nome).subscribe({
    //       next: (resposta: Post[]) => {
    //         this.selectedTags = resposta;
    //         this.applyFilters();
    //       }
    //     })
    //   }
    // }
  
    // applyFilters(): void {
    //   this.postsUsuario = [...this.selectedCategories, ...this.selectedTags];
    // }

}
