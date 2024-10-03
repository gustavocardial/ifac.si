import { Component, OnInit } from '@angular/core';
import { PostService } from '../../service/post.service';
import { Post } from '../../model/post';

@Component({
  selector: 'app-posts-app',
  templateUrl: './posts-app.component.html',
  styleUrl: './posts-app.component.css'
})
export class PostsAppComponent implements OnInit{
  posts: Post[] = Array<Post>();

  constructor (
    private postServico : PostService
  ) {}

  ngOnInit(): void {
    this.postServico.get().subscribe({
      next: (resposta: Post[]) => {
        this.posts = resposta;
      }
    });
  }

  
}
