import { Component, ElementRef, OnInit, QueryList, Renderer2, ViewChild, ViewChildren } from '@angular/core';
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

  private listeners: (() => void)[] = [];

  constructor (
    private postServico : PostService,
    private renderer: Renderer2
  ) {}

  ngOnInit(): void {
    this.postServico.get().subscribe({
      next: (resposta: Post[]) => {
        this.posts = resposta;
      }
    });
  }

  ngAfterViewInit(): void {
    this.deleteButtons.forEach(button => {
      const listener = this.renderer.listen(button.nativeElement, 'click', (event) => {
        alert('Delete selecionado');
      });
      this.listeners.push(listener);
    });

    this.editButtons.forEach(button => {
      const listener = this.renderer.listen(button.nativeElement, 'click', (event) => {
        alert('Edit selecionado');
      });
      this.listeners.push(listener);
    });

  }

  ngOnDestroy(): void {
    this.listeners.forEach(listener => listener());
  }
  
}
