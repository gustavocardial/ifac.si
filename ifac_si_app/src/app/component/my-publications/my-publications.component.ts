import { Component, ElementRef, OnDestroy, OnInit, QueryList, Renderer2, ViewChildren } from '@angular/core';
import { LoginService } from '../../service/login.service';
import { Subscription } from 'rxjs';
import { Usuario } from '../../model/usuario';
import { PostService } from '../../service/post.service';
import { Post } from '../../model/post';
import { PageRequest } from '../../model/page-request';
import { PageResponse } from '../../model/page-response';
import { Router } from '@angular/router';

@Component({
  selector: 'app-my-publications',
  templateUrl: './my-publications.component.html',
  styleUrl: './my-publications.component.css'
})

export class MyPublicationsComponent implements OnInit, OnDestroy {
  @ViewChildren('deleteButton') deleteButtons!: QueryList<ElementRef>;
  @ViewChildren('editButton') editButtons!: QueryList<ElementRef>;
  @ViewChildren('showButton') showButtons!: QueryList<ElementRef>;
  
  private subscription: Subscription = new Subscription();
  usuario: Usuario = <Usuario>{};
  postsUsuario: Post[] = Array<Post>();
  paginaRequisicao: PageRequest = new PageRequest();
  paginaResposta: PageResponse<Post> = <PageResponse<Post>>{};
  termoBusca: string = '';
  selectedCategories: Post[] = Array<Post>(); // Lista de categorias selecionadas
  selectedTags: Post[] = Array<Post>(); // Lista de tags selecionadas
  ordenacao: string = 'asc';
  show: boolean = false;
  postIdToDelete!: number;

  private listeners: (() => void)[] = [];

  constructor(
    private servicoLogin: LoginService, 
    private servicoPost: PostService,
    private renderer: Renderer2,
    private router: Router,) {}

  ngOnInit(): void {
    this.getAll();
  }

  getAll(): void {
    this.subscription = this.servicoLogin.usuarioAutenticado.subscribe({
      next: (usuario: Usuario) => {
        this.usuario = usuario;
        this.carregarPostsUsuario();

        console.log('usuario: ', this.usuario);
      }
    })
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
        this.postIdToDelete = +button.nativeElement.getAttribute('data-post-id');
        this.showDelete();
      });
      this.listeners.push(listener);
    });

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

  carregarPostsUsuario(): void {
    
    this.servicoPost.get(undefined, this.paginaRequisicao).subscribe({
      next: (resposta: PageResponse<Post>) => {
        this.paginaResposta = resposta;

        this.postsUsuario = resposta.content.filter((post: Post) => post.usuario && post.usuario?.id == this.usuario.id);
        // this.postsUsuario = resposta.filter((post: Post) => post.usuario?.id === this.usuario.id);      
      }
    })
  }

  mudarPagina(pagina: number): void {
    this.paginaRequisicao.page = pagina;
    this.carregarPostsUsuario();
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  atualizarOrdem(ordem: string) {
    this.ordenacao = ordem;
    this.ordenarPosts();
  }

  ordenarPosts() {
    this.postsUsuario.sort((a, b) => {
      if (this.ordenacao === 'asc') {
        return a.titulo.localeCompare(b.titulo);
      } else {
        return b.titulo.localeCompare(a.titulo);
      }
    });
  }

  showDelete(): void {
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

  deletePost() {
    this.servicoPost.delete(this.postIdToDelete).subscribe({
      complete: () => {
        this.ngOnInit();
        this.showDelete();// Fecha a confirmação de deleção
      }
    });
  }

  getCategoria(id: number | null): void {
    if (id === null) {
      // Lógica para quando não há categoria selecionada
      this.selectedCategories = [];
      this.applyFilters();  // Exemplo: limpa os posts se não houver categoria
    } else {
      this.servicoPost.getByCategoria(id).subscribe({
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
      this.servicoPost.getByTag(nome).subscribe({
        next: (resposta: Post[]) => {
          this.selectedTags = resposta;
          this.applyFilters();
        }
      })
    }
  }

  applyFilters(): void {
    this.postsUsuario = [...this.selectedCategories, ...this.selectedTags];
  }
}
