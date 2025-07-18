import { Component } from '@angular/core';

@Component({
  selector: 'app-trash-view',
  templateUrl: './trash-view.component.html',
  styleUrl: './trash-view.component.css'
})
export class TrashViewComponent {
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

}
