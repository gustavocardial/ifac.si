import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PostService } from '../../service/post.service';
import { Post } from '../../model/post';

@Component({
  selector: 'app-view-post',
  templateUrl: './view-post.component.html',
  styleUrls: ['./view-post.component.css']
})
export class ViewPostComponent {
  constructor (
    private router: Router,
    private route: ActivatedRoute,
    private servico: PostService
  ) {}

  post: Post = <Post>{};

  ngOnInit(): void {
    const id = this.route.snapshot.queryParamMap.get('postId');

    console.log(id);
    if (id) {
      this.servico.getById(+id).subscribe({
        next: (resposta: Post) => {
          this.post = resposta;
        }
      })
    }
  }
}
