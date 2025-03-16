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
import { statusPost } from '../../model/enum/statusEnum';
// import { ImagemHandler } from '../../model/imagemHandler';
import { UsuarioService } from '../../service/usuario.service';
import { Usuario } from '../../model/usuario';
import { PublicacaoEnum } from '../../model/enum/publicacaoEnum';
import { visibilidadePost } from '../../model/enum/visibilidadeEnum';
import { Subscription } from 'rxjs';
import { LoginService } from '../../service/login.service';

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
  private capaListener: (() => void) | undefined;
  private statusButtonListener: (() => void) | undefined;
  private visibilidadeButtonListener: (() => void) | undefined;
  private publicacaoButtonListener: (() => void) | undefined;

  @ViewChild('category') categoryButton!: ElementRef;
  @ViewChild('tags') tagButton!: ElementRef;
  @ViewChild('status') statusButton!: ElementRef;
  @ViewChild('newTag') newTag!: ElementRef;
  @ViewChild('tag') buttonTag!: ElementRef;
  @ViewChild('imagemCapa') capaInput!: ElementRef;
  @ViewChild('editarVisibilidade') visibiEdit!: ElementRef;
  @ViewChild('editarPublicacao') publiEdit!: ElementRef;
  @ViewChild('editarStatus') statusEdit!: ElementRef;
  private subscription: Subscription = new Subscription();

  constructor (
    private servicoPost: PostService,
    private router: Router,
    private route: ActivatedRoute,
    private renderer: Renderer2,
    private servicoCategoria: CategoriaService,
    private servicoTag: TagService,
    private servicoAlerta: AlertaService,
    private servicoUsuario: UsuarioService,
    private servicoLogin: LoginService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.queryParamMap.get('postId');
    if (id){
      this.servicoPost.getById(+id).subscribe({
        next: (resposta: Post) => {
          this.post = resposta;

          // console.log ("Post", resposta);
          
          // this.tags = resposta.tags;
        }
      });

      // this.servicoTag.getTagByPost(this.post).subscribe({
      //   next: (resposta: tags[]) => {
      //     this.tagsList = resposta; 
      //     console.log (this.tagsList);
      //   }
      // })

      this.servicoTag.get().subscribe({
        next: (resposta: tags[]) => {
          // this.tags = resposta;
          this.tagsList = resposta.filter((tag: tags) => this.post?.tags?.some(postTag => Number(postTag.id) === Number(tag.id)));
        
          console.log('Tags:', this.tagsList); 
        }
      });  
    }

    this.subscription = this.servicoLogin.usuarioAutenticado.subscribe({
      next: (usuario: Usuario) => {
        this.post.usuario = usuario;

        // console.log('usuario: ', usuario);
      }
    })

    this.servicoCategoria.get().subscribe({
      next: (resposta: Categoria[]) => {
        this.categorias = resposta;
      }
    });

    this.carregarUsuario();
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

    this.capaListener = this.renderer.listen(this.capaInput.nativeElement, 'change', (event) => {
      console.log ("tem imagem de capa");
    })

    this.visibilidadeButtonListener = this.renderer.listen(this.visibiEdit.nativeElement, 'click', (event) => {
      this.visibilidadeContent = !this.visibilidadeContent;
    })

    this.publicacaoButtonListener = this.renderer.listen(this.publiEdit.nativeElement, 'click', (event) => {
      this.publicacaoContent = !this.publicacaoContent;
    })

    this.statusButtonListener = this.renderer.listen(this.statusEdit.nativeElement, 'click', (event) => {
      this.statusContent = !this.statusContent;
    })

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
  visibilidadeContent: boolean = true;
  publicacaoContent: boolean = true;
  statusContent: boolean = true;

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
    this.extractBase64Images(this.editorText);
  }

  private extractBase64Images(content: string): File[] {
    const imgRegex = /<img[^>]+src=["'](data:image\/(png|jpeg|jpg);base64,([^"']+))["']/g;
    const files: File[] = [];
    let match;
    let index = 0;
  
    while ((match = imgRegex.exec(content)) !== null) {
      const mimeType = match[2]; // Tipo da imagem (png, jpeg, jpg)
      const base64Data = match[3]; // Dados base64 da imagem
  
      // Converter base64 para Blob
      const byteCharacters = atob(base64Data);
      const byteNumbers = new Array(byteCharacters.length);
      for (let i = 0; i < byteCharacters.length; i++) {
        byteNumbers[i] = byteCharacters.charCodeAt(i);
      }
      const byteArray = new Uint8Array(byteNumbers);
      const blob = new Blob([byteArray], { type: `image/${mimeType}` });
  
      // Criar um arquivo
      const file = new File([blob], `imagem_${index}.${mimeType}`, { type: `image/${mimeType}` });
      files.push(file);
      index++;
    }
  
    console.log('Imagens extraídas:', files);
    return files;
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

  private carregarUsuario(): void {
    if (!this.post.usuario) {
      this.servicoUsuario.getById(1).subscribe({
        next: (resposta: Usuario) => {
          this.post.usuario = resposta;
        }
      });
    }
  }

  saveContent(): void {

    const formData = new FormData();

    // Adiciona os campos do post
    formData.append('titulo', this.post.titulo);
    formData.append('texto', this.post.texto);

    if(this.post.usuario?.id) {
      formData.append('usuarioId', this.post.usuario.id.toString());
    }

    if (this.post.categoria?.id) {
      formData.append('categoriaId', this.post.categoria.id.toString());
    }
    
    if (this.post.legenda) {
      formData.append('legenda', this.post.legenda);
    }
  
    // Adiciona o status (com valor default se necessário)
    formData.append('status', this.post.EStatus || statusPost.Publicado);
  
    // Se tiver tags, adiciona cada uma
    if (this.post.tags && this.post.tags.length > 0) {
      this.post.tags.forEach(tag => {
        formData.append('tags', tag.nome);
      });
    }
  
    // Se tiver imagem de capa
    if (this.capaInput?.nativeElement?.files[0]) {
      formData.append('imagemCapa', this.capaInput.nativeElement.files[0]);
    }
  
    // Você também precisará modificar seu PostService para usar o FormData
    this.servicoPost.save(formData).subscribe({
      complete: () => {
        this.servicoAlerta.enviarAlerta({
          tipo: ETipoAlerta.SUCESSO,
          mensagem: "Post salvo com sucesso"
        });
        this.router.navigate(['/view_posts']);
      },
      error: (erro) => {
        this.servicoAlerta.enviarAlerta({
          tipo: ETipoAlerta.ERRO,
          mensagem: "Erro ao salvar o post"
        });
        console.error('Erro ao salvar:', erro);
      }
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

  public publicacaoOptions = Object.keys(PublicacaoEnum)
    .filter(key => isNaN(Number(key))) // Filtra apenas as chaves, ignorando os números
    .map(key => ({ label: key, value: PublicacaoEnum[key as keyof typeof PublicacaoEnum] }));

  public statusOptions = Object.keys(statusPost)
    .filter(key => isNaN(Number(key))) // Filtra apenas as chaves, ignorando os números
    .map(key => ({ label: key, value: statusPost[key as keyof typeof statusPost] }));

  public visibilidadeOptions = Object.keys(visibilidadePost)
  .filter(key => isNaN(Number(key))) // Filtra apenas as chaves, ignorando os números
  .map(key => ({ label: key, value: visibilidadePost[key as keyof typeof visibilidadePost] }));

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
