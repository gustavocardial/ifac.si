import { Component, ElementRef, OnInit, Renderer2, ViewChild } from '@angular/core';
import { Post } from '../../model/post';
import { PostService } from '../../service/post.service';
import { EditorChangeContent, EditorChangeSelection } from 'ngx-quill';
import { ActivatedRoute, Router } from '@angular/router';
import { Categoria } from '../../model/categoria';
import { CategoriaService } from '../../service/categoria.service';
import { TagService } from '../../service/tag.service';
import { tags } from '../../model/tag';
import { AlertaService } from '../../service/alerta.service';
import { ETipoAlerta } from '../../model/e-tipo-alerta';
import { statusPost } from '../../model/statusEnum';
import { ImagemHandler } from '../../model/imagemHandler';
import { UsuarioService } from '../../service/usuario.service';
import { Usuario } from '../../model/usuario';

@Component({
  selector: 'app-add-new-post',
  templateUrl: './add-new-post.component.html',
  styleUrl: './add-new-post.component.css'
})
export class AddNewPostComponent implements OnInit{
  private statusListener: (() => void) | undefined;
  private categoryListener: (() => void) | undefined;
  private tagListener: (() => void) | undefined;
  private tagListen: (() => void) | undefined;

  @ViewChild('category') categoryButton!: ElementRef;
  @ViewChild('tags') tagButton!: ElementRef;
  @ViewChild('status') statusButton!: ElementRef;
  @ViewChild('newTag') newTag!: ElementRef;
  @ViewChild('tag') buttonTag!: ElementRef;
  @ViewChild('imagemCapa') capaInput!: ElementRef;

  constructor (
    private servicoPost: PostService,
    private router: Router,
    private route: ActivatedRoute,
    private renderer: Renderer2,
    private servicoCategoria: CategoriaService,
    private servicoTag: TagService,
    private servicoAlerta: AlertaService,
    private servicoUsuario: UsuarioService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.queryParamMap.get('postId');
    if (id){
      this.servicoPost.getById(+id).subscribe({
        next: (resposta: Post) => {
          this.post = resposta;
          
          // this.tags = resposta.tags;
        }
      });
    }

    this.servicoTag.get().subscribe({
      next: (resposta: tags[]) => {
        // this.tags = resposta;
        this.tagsList = resposta.map(tag => ({
          id: tag.id,
          nome: tag.nome
        }));
        console.log('Tags:', this.tagsList); 
      }
    });  

    this.servicoCategoria.get().subscribe({
      next: (resposta: Categoria[]) => {
        this.categorias = resposta;
      }
    });
    // if (id) alert(`Chegou post com id ${id}`);
  }

  ngAfterViewInit(): void {
    // Adicionando o listener para o botão de categorias
    this.statusListener = this.renderer.listen(this.categoryButton.nativeElement, 'click', (event) => {
      this.onCategoryClick();
      // this.initializeCategoryDropdownListener();
    });

    // Adicionando o listener para o botão de tags
    this.tagListener = this.renderer.listen(this.tagButton.nativeElement, 'click', (event) => {
      this.onTagClick();
      // this.initializeTagDropdownListener();
    });

    this.statusListener = this.renderer.listen(this.statusButton.nativeElement, 'click', (event) => {
      this.onStatusClick();
      // this.initializeTagDropdownListener();
    });

    this.tagListen = this.renderer.listen(this.buttonTag.nativeElement, 'click', (event) => {
      this.onTag();
      // this.initializeTagDropdownListener();
    });
  }

  // ngOnInit(): void {
  //   this.get();    
  // }
  post: Post = <Post>{};
  categorias: Categoria[] = Array<Categoria>();
  tagsList: tags[] = Array<tags>();
  accordionView: boolean = false;
  buttonS: boolean = false;
  buttonC: boolean = false;
  buttonT: boolean = false;
  filtersT: boolean = false;
  numberOfImages: number = 0;

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

    this.countImageTags(this.editorText);
  }

  private countImageTags(content: string): void {
    // Regex simples para encontrar tags <img>
    const imgRegex = /<img[^>]*>/g;
    
    // Encontrar todas as tags img
    const matches = content.match(imgRegex);
    
    // Atualizar o contador de imagens
    this.numberOfImages = matches ? matches.length : 0;
    
    console.log('Número de imagens encontradas:', this.numberOfImages);
  }

  saveContent(): void {

  //    // Se tiver arquivo de capa, prepara para envio
  // if (this.capaInput) {
  //   ImagemHandler.prepararImagem(this.capaInput, this.post).then(imagemData => {
  //     // Cria um FormData só para a imagem
  //     const formData = new FormData();
  //     formData.append('imagem', imagemData.arquivo);

  //     // Faz o upload da imagem primeiro
  //     this.servicoPost.uploadImagem(formData).subscribe({
  //       next: (imagemResponse: Imagem) => {
  //         // Atualiza o post com a imagem de capa retornada
  //         this.post.imagemCapa = imagemResponse;
  //       }
  //     })

  //   })
  // }
    

    if(!this.post.EStatus) {
      this.post.EStatus = statusPost.publicado;
    }

    if(!this.post.usuario) {
      this.servicoUsuario.getById(1).subscribe({
        next: (resposta: Usuario) => {
          this.post.usuario = resposta;
        }
      })
    }

    console.log(this.post);

    this.servicoPost.save(this.post).subscribe({
      complete: () => 
      this.servicoAlerta.enviarAlerta({
        tipo: ETipoAlerta.SUCESSO,
        mensagem: "Post salvo com sucesso"
      })
    });  
    // Aqui você pode fazer algo com o conteúdo do editor
    // console.log(this.escapeHtml(this.data.content));
    console.log('Conteúdo salvo:', this.post.texto);
    this.router.navigate(['/view_posts']);
  }

  getById(id: number): void {
    this.servicoPost.getById(id).subscribe({
      next: (resposta: Post) => {
        this.post = resposta;
      }
    })
  }

  onTagClick(): void {
    this.buttonT = !this.buttonT;
  }

  onStatusClick(): void {
    this.buttonS = !this.buttonS;
  }

  onCategoryClick(): void {
    this.buttonC = !this.buttonC;
  }

  ngOnDestroy(): void {
    if (this.categoryListener) {
      this.categoryListener();
    }
    if (this.tagListener) {
      this.tagListener();
    }
    if (this.statusListener) {
      this.statusListener();
    }
  }

  onCategoriaSelect(categoria: Categoria): void {
    this.post.categoria = categoria;
  }

  addTag(): void {
    let newTag = this.addTagPost(this.newTag.nativeElement.value)
    this.tagsList.push(newTag);
    console.log(this.tagsList);
    this.newTag.nativeElement.value = '';
  }

  onTag(): void {
    this.filtersT = !this.filtersT;
  }

  addTagPost(tagName: string): tags {
    const newId = this.tagsList.length > 0 
        ? this.tagsList[this.tagsList.length - 1].id + 1 
        : 1;

    // Cria uma nova tag
    const newTag = this.createTag(newId, tagName);
    newTag.id = newId; // Atribui o ID gerado
    newTag.nome = tagName; // Atribui o nome da tag
    // newTag.posts = []; // Inicializa a lista de posts vazia

    // Adiciona a nova tag à lista de tags
    // this.tagsList.push(newTag);

    return newTag;
  }

  createTag(id: number, nome: string): tags {
    return {
        id,
        nome,
        // posts: []
    };
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
