import { Component, ElementRef, OnInit, Renderer2, ViewChild } from '@angular/core';
import { PostService } from '../../service/post.service';
import { Post } from '../../model/post';

@Component({
  selector: 'app-posts-app',
  templateUrl: './posts-app.component.html',
  styleUrl: './posts-app.component.css'
})
export class PostsAppComponent implements OnInit{
  @ViewChild('deleteButton') deleteButton! : ElementRef;
  @ViewChild('editButton') editButton! : ElementRef;
  posts: Post[] = Array<Post>();

  private editButtonListener: (() => void) | undefined;
  private deleteButtonListener: (() => void) | undefined;

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
    this.editButtonListener = this.renderer.listen(this.editButton.nativeElement, 'click', (event) => {
      alert('Edit selecionado');
    })

    this.deleteButtonListener = this.renderer.listen(this.deleteButton.nativeElement, 'click', (event) => {
      alert('Delete selecionado');
    })
  }

  ngOnDestroy(): void {
    if (this.editButtonListener) {
      this.editButtonListener();
    }
    if (this.deleteButtonListener) {
      this.deleteButtonListener();
    }
  }
  
}
