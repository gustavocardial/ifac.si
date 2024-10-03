import { Component, ViewChild } from '@angular/core';
import { Post } from '../../model/post';
import { PostService } from '../../service/post.service';
import { EditorChangeContent, EditorChangeSelection } from 'ngx-quill';

@Component({
  selector: 'app-add-new-post',
  templateUrl: './add-new-post.component.html',
  styleUrl: './add-new-post.component.css'
})
export class AddNewPostComponent {
  constructor (private servicoPost: PostService) {}
  
  // ngOnInit(): void {
  //   this.get();    
  // }
  post: Post = <Post>{};

  title = 'teste';
  @ViewChild('editor') editor: any;

  escapeHtml(text: string): string {
    return text
      .replace(/&/g, '&amp;')
      .replace(/</g, '&lt;')
      .replace(/>/g, '&gt;')
      .replace(/"/g, '&quot;')
      .replace(/'/g, '&#39;');
  }
  
  editorText = '';

  changedEditor(event: EditorChangeContent | EditorChangeSelection) {
    console.log('Editor mudou ', event);
    this.editorText = event['editor']['root']['innerHTML'];
  }

  saveContent(): void {
    this.servicoPost.save(this.post).subscribe({complete: () => {console.log (this.post)}});  
    // Aqui você pode fazer algo com o conteúdo do editor
    // console.log(this.escapeHtml(this.data.content));
    console.log('Conteúdo salvo:', this.post.texto);
  }

  getById(id: number): void {
    this.servicoPost.getById(id).subscribe({
      next: (resposta: Post) => {
        this.post = resposta;
      }
    })
  }

  // get(): void {
  //   this.servico.get().subscribe({
  //     next: (resposta: data[]) => {
  //       this.lista = resposta;
  //     }
  //   })
  // }

  modules = {
    toolbar: [
      ['bold', 'italic', 'underline', 'strike'],        // toggled buttons
      ['blockquote', 'code-block'],
  
      [{ 'header': 1 }, { 'header': 2 }],               // custom button values
      [{ 'list': 'ordered'}, { 'list': 'bullet' }],
      [{ 'script': 'sub'}, { 'script': 'super' }],      // superscript/subscript
      [{ 'indent': '-1'}, { 'indent': '+1' }],          // outdent/indent
      [{ 'direction': 'rtl' }],                         // text direction
  
      [{ 'size': ['small', false, 'large', 'huge'] }],  // custom dropdown
      [{ 'header': [1, 2, 3, 4, 5, 6, false] }],
  
      [{ 'color': [] }, { 'background': [] }],          // dropdown with defaults from theme
      [{ 'font': [] }],
      [{ 'align': [] }],
  
      ['clean'],                                         // remove formatting button
  
      ['link', 'image', 'video']                         // link and image, video
    ]
  };
}
