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
      acao: "ARQUIVADO",
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
          nome: "Vaga"
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
    },
    {
      "id": 2,
      "acao": "ARQUIVADO",
      "dataAcao": "2025-07-08T10:00:00",
      "usuarioResponsavelId": 15,
      "motivo": "Erro na digitação do título",
      "post": {
        "id": 56,
        "titulo": "Curso Rápido de HTML",
        "texto": "Aprenda os conceitos básicos de HTML em poucas horas...",
        "usuario": {
          "id": 9,
          "email": "dev@ifac.edu.br",
          "nomeUsuario": "dev.front",
          "senha": "**********",
          "cargo": "AUTOR",
          "ativo": true
        },
        "categoria": {
          "id": 2,
          "nome": "Pesquisa"
        },
        "tags": [
          { "id": 5, "nome": "HTML" }
        ],
        "data": "2025-07-20T08:00:00",
        "legenda": "Aprenda HTML em um dia",
        "mensagemReprovacao": null,
        "imagens": [],
        "imagemCapa": null,
        "status": "REMOVIDO",
        "visibilidade": "PRIVADO",
        "publicacao": "NAO_PUBLICADO"
      }
    },
    {
      "id": 3,
      "acao": "ARQUIVADO",
      "dataAcao": "2025-07-01T15:30:00",
      "usuarioResponsavelId": 11,
      "motivo": "Conteúdo desatualizado",
      "post": {
        "id": 57,
        "titulo": "Ferramentas para Desenvolvimento Web em 2022",
        "texto": "Veja as principais ferramentas usadas em 2022...",
        "usuario": {
          "id": 10,
          "email": "editor@ifac.edu.br",
          "nomeUsuario": "editor.web",
          "senha": "**********",
          "cargo": "EDITOR",
          "ativo": true
        },
        "categoria": {
          "id": 4,
          "nome": "Projeto"
        },
        "tags": [
          { "id": 3, "nome": "Desenvolvimento" },
          { "id": 7, "nome": "Ferramentas" }
        ],
        "data": "2022-09-15T11:00:00",
        "legenda": "Ferramentas populares em 2022",
        "mensagemReprovacao": null,
        "imagens": [],
        "imagemCapa": null,
        "status": "REMOVIDO",
        "visibilidade": "PUBLICO",
        "publicacao": "PUBLICADO"
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
