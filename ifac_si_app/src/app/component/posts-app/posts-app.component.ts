import { ChangeDetectorRef, Component, ElementRef, OnInit, QueryList, Renderer2, ViewChild, viewChildren, ViewChildren } from '@angular/core';
import { PostService } from '../../service/post.service';
import { Post } from '../../model/post';
import { Router } from '@angular/router';
import { AlertaService } from '../../service/alerta.service';
import { ETipoAlerta } from '../../model/enum/e-tipo-alerta';
import { PageRequest } from '../../model/page-request';
import { PageResponse } from '../../model/page-response';
import { Subscription } from 'rxjs';
import { LoginService } from '../../service/login.service';
import { Usuario } from '../../model/usuario';

@Component({
  selector: 'app-posts-app',
  templateUrl: './posts-app.component.html',
  styleUrl: './posts-app.component.css'
})
export class PostsAppComponent implements OnInit{
  @ViewChildren('deleteButton') deleteButtons!: QueryList<ElementRef>;
  @ViewChildren('editButton') editButtons!: QueryList<ElementRef>;
  @ViewChildren('showButton') showButtons!: QueryList<ElementRef>;
  @ViewChildren('reprovButton') reprovButtons!: QueryList<ElementRef>;
  
  private subscription: Subscription = new Subscription();
  usuario: Usuario = <Usuario>{};
  posts: Post[] = Array<Post>();
  show: boolean = false;
  postId!: number;
  paginaRequisicao: PageRequest = new PageRequest();
  paginaResposta: PageResponse<Post> = <PageResponse<Post>>{};
  termoBusca: string = '';
  selectedCategories: Post[] = Array<Post>(); // Lista de categorias selecionadas
  selectedTags: Post[] = Array<Post>(); // Lista de tags selecionadas
  ordenacao: string = 'asc';
  acaoModal: 'deletar' | 'reprovar' | null = null;

  private listeners: (() => void)[] = [];

  constructor (
    private postServico : PostService,
    private renderer: Renderer2,
    private cdRef: ChangeDetectorRef,
    private router: Router,
    private servicoAlerta: AlertaService,
    private servicoLogin: LoginService
  ) {}

  ngOnInit(): void {
    this.getAll();
    this.getUser();
  }

  ngAfterViewInit(): void {
    this.setupButtonListeners();
  }

  ngAfterViewChecked(): void {
    this.setupButtonListeners();  // Reconfigura os ouvintes após cada mudança na view
  }

  private setupButtonListeners(): void {
    // Remove os listeners existentes
    this.listeners.forEach(listener => listener());
    this.listeners = [];

    // Adiciona novos listeners
    this.deleteButtons.forEach(button => {
      const listener = this.renderer.listen(button.nativeElement, 'click', () => {
        // alert('Delete selecionado');
        this.postId = +button.nativeElement.getAttribute('data-post-id');
        this.acaoModal = 'deletar';
        this.showModal();
      });
      this.listeners.push(listener);
    });

    this.reprovButtons.forEach(button => {
      const listener = this.renderer.listen(button.nativeElement, 'click', () => {
        this.postId = +button.nativeElement.getAttribute('data-post-id');
        this.acaoModal = 'reprovar';
        this.showModal();
      });
      this.listeners.push(listener);
    })

    this.editButtons.forEach((button, index) => {
      const postId = button.nativeElement.getAttribute('data-post-id');
      const listener = this.renderer.listen(button.nativeElement, 'click', () => {
        console.log ("Editando");
        this.editPost(postId);
      });
      this.listeners.push(listener);
    });

    this.showButtons.forEach((button, index) => {
      const postId = button.nativeElement.getAttribute('data-post-id');
      const listener = this.renderer.listen(button.nativeElement, 'click', () => {
        // console.log ("Visualizando");
        this.viewPost(postId);
      });
      this.listeners.push(listener);
    })
  }

  ngOnDestroy(): void {
    this.listeners.forEach(listener => listener());
  }
  
  showModal(): void {
    this.show = !this.show
  }

  editPost(postId: number) {
    this.router.navigate(['/administration/autor/new_post'], { 
      queryParams: { postId: postId }
    });
  }

  viewPost(postId: number) {
    this.router.navigate(['/view_post'], { 
      queryParams: { postId: postId }
    });
  }

  deletePost(postId: number) {
    this.postServico.delete(postId).subscribe({
      complete: () => {
        this.ngOnInit();
        this.showModal();// Fecha a confirmação de deleção
      }
    });
  }

  reprovarPost(postId: number) {
    console.log (postId);
    console.log ('teste');
    this.showModal();
  }

  getCategoria(id: number | null): void {
    if (id === null) {
      // Lógica para quando não há categoria selecionada
      this.selectedCategories = [];
      this.applyFilters();  // Exemplo: limpa os posts se não houver categoria
    } else {
      this.postServico.getByCategoria(id).subscribe({
        next: (resposta: Post[]) => {
          this.selectedCategories = resposta;
          this.applyFilters();
        }
      })
    }

    // this.applyFilters();
  }

  getByTag(nome: string | null): void {
    if (nome === null || nome === '') {
      // Lógica para quando não há tag selecionada
      this.selectedTags = [];
      this.applyFilters();
    } else {
      this.postServico.getByTag(nome).subscribe({
        next: (resposta: Post[]) => {
          this.selectedTags = resposta;
          this.applyFilters();
        }
      })
    }
  }

  applyFilters(): void {
    this.posts = [...this.selectedCategories, ...this.selectedTags];
  }

  getAll(termoBusca?: string | undefined): void {
    this.postServico.get(termoBusca, this.paginaRequisicao).subscribe({
      next: (resposta: PageResponse<Post>) => {
        this.posts = resposta.content;
        this.paginaResposta = resposta;

        this.ordenarPosts();
        setTimeout(() => this.setupButtonListeners(), 0);
      }
    });
  }

  getUser(): void {
    this.subscription = this.servicoLogin.usuarioAutenticado.subscribe({
      next: (usuario: Usuario) => {
        this.usuario = usuario;
      }
    })
  }

  mudarPagina(pagina: number): void {
    this.paginaRequisicao.page = pagina;
    this.getAll();
  }

  atualizarOrdem(ordem: string) {
    this.ordenacao = ordem;
    this.ordenarPosts();
  }

  ordenarPosts() {
    this.posts.sort((a, b) => {
      if (this.ordenacao === 'asc') {
        return a.titulo.localeCompare(b.titulo);
      } else {
        return b.titulo.localeCompare(a.titulo);
      }
    });
  }

  getMensagemLinha1(): string {
  switch (this.acaoModal) {
    case 'deletar':
      return 'Você tem certeza que deseja deletar este item?';
    case 'reprovar':
      return 'Deseja reprovar este post e torná-lo privado?';
    default:
      return 'Tem certeza que deseja continuar com esta ação?';
    }
  }

  getMensagemLinha2(): string {
    switch (this.acaoModal) {
      case 'deletar':
        return 'O post será mantido na lixeira por um período limitado antes da exclusão permanente.';
      case 'reprovar':
        return 'O autor poderá fazer os ajustes necessários e reenviar para nova avaliação.';
      default:
        return '';
    }
  }

  confirmarAcao() {
    switch (this.acaoModal) {
      case 'deletar':
        this.deletePost(this.postId);
        break;
      case 'reprovar':
        this.reprovarPost(this.postId);
        break;
    }
  }


}
