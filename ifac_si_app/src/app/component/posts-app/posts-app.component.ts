import { ChangeDetectorRef, Component, ElementRef, OnInit, QueryList, Renderer2, ViewChild, ViewChildren } from '@angular/core';
import { PostService } from '../../service/post.service';
import { Post } from '../../model/post';

@Component({
  selector: 'app-posts-app',
  templateUrl: './posts-app.component.html',
  styleUrl: './posts-app.component.css'
})
export class PostsAppComponent implements OnInit{
  @ViewChildren('deleteButton') deleteButtons!: QueryList<ElementRef>;
  @ViewChildren('editButton') editButtons!: QueryList<ElementRef>;
  posts: Post[] = Array<Post>();
  show: boolean = false;

  private listeners: (() => void)[] = [];

  constructor (
    private postServico : PostService,
    private renderer: Renderer2,
    private cdRef: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.postServico.get().subscribe({
      next: (resposta: Post[]) => {
        this.posts = resposta;
        setTimeout(() => this.setupButtonListeners(), 0);
      }
    });
  }

  ngAfterViewInit(): void {
    this.setupButtonListeners();
  }

  private setupButtonListeners(): void {
    // Remove os listeners existentes
    this.listeners.forEach(listener => listener());
    this.listeners = [];

    // Adiciona novos listeners
    this.deleteButtons.forEach(button => {
      const listener = this.renderer.listen(button.nativeElement, 'click', () => {
        alert('Delete selecionado');
        this.showDelete();
      });
      this.listeners.push(listener);
    });

    this.editButtons.forEach(button => {
      const listener = this.renderer.listen(button.nativeElement, 'click', () => {
        alert('Edit selecionado');
      });
      this.listeners.push(listener);
    });
  }

  ngOnDestroy(): void {
    this.listeners.forEach(listener => listener());
  }
  
  showDelete(): void {
    this.show = !this.show
  }
}
