import { Component, ElementRef, OnInit, Renderer2, ViewChild } from '@angular/core';
import { Post } from '../../model/post';
import { PostService } from '../../service/post.service';
import { EditorChangeContent, EditorChangeSelection } from 'ngx-quill';
import { ActivatedRoute, Router } from '@angular/router';
import { Categoria } from '../../model/categoria';
import { CategoriaService } from '../../service/categoria.service';
import { TagService } from '../../service/tag.service';
import { Tag } from '../../model/tag';
import { AlertaService } from '../../service/alerta.service';
import { ETipoAlerta } from '../../model/enum/e-tipo-alerta';
import { EStatus } from '../../model/enum/EStatus';
// import { ITags } from '../../model/ITags';
// import { ImagemHandler } from '../../model/imagemHandler';
import { UsuarioService } from '../../service/usuario.service';
import { Usuario } from '../../model/usuario';
import { EPublicacao } from '../../model/enum/EPublicacao';
import { EVisibilidade } from '../../model/enum/EVisibilidade';
import { Subscription } from 'rxjs';
import { LoginService } from '../../service/login.service';
import { ITags } from '../../model/ITags';
import { Imagem } from '../../model/imagem';

@Component({
  selector: 'app-add-new-post',
  templateUrl: './add-new-post.component.html',
  styleUrl: './add-new-post.component.css'
})
export class AddNewPostComponent implements OnInit{
  private statusListener: (() => void) | undefined;
  private categoryListener: (() => void) | undefined;
  private tagListener: (() => void) | undefined;
  private optionListener: (() => void) | undefined;
  private tagListen: (() => void) | undefined;
  private capaListener: (() => void) | undefined;
  private statusButtonListener: (() => void) | undefined;
  private visibilidadeButtonListener: (() => void) | undefined;
  private publicacaoButtonListener: (() => void) | undefined;
  private images: File[] = [];
  post: Post = <Post>{};
  usuarioLogado: Usuario = <Usuario>{};
  categorias: Categoria[] = Array<Categoria>();
  tagsList: ITags[] = Array<ITags>();
  tagsOptions: Tag[] = Array<Tag>();
  accordionView: boolean = false;
  buttonS: boolean = false;
  buttonC: boolean = false;
  buttonT: boolean = false;
  filtersT: boolean = false;
  optionsButton: boolean = false;
  numberOfImages: number = 0;
  visibilidadeContent: boolean = true;
  selectedVisibilidade?: EVisibilidade ;
  publicacaoContent: boolean = true;
  selectedPublicacao?: EPublicacao;
  statusContent: boolean = true;
  editingTagId: number | null = null;
  isEditing: boolean = false;
  minDateTime: string = '';
  EStatus = EStatus;
  // dataSelecionada: string = '';
  @ViewChild('category') categoryButton!: ElementRef;
  @ViewChild('tags') tagButton!: ElementRef;
  @ViewChild('status') statusButton!: ElementRef;
  @ViewChild('newTag') newTag!: ElementRef;
  @ViewChild('tag') buttonTag!: ElementRef;
  @ViewChild('optionTag') buttonOption!: ElementRef;
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
    const hoje = new Date();
    hoje.setHours(0, 0, 0, 0);  // Zera horas, minutos, segundos e milissegundos
    const ano = hoje.getFullYear();
    const mes = String(hoje.getMonth() + 1).padStart(2, '0');
    const dia = String(hoje.getDate()).padStart(2, '0');
    const horas = String(hoje.getHours()).padStart(2, '0');
    const minutos = String(hoje.getMinutes()).padStart(2, '0');

    this.minDateTime = `${ano}-${mes}-${dia}T${horas}:${minutos}`;

    const id = this.route.snapshot.queryParamMap.get('postId');
    if (id){
      // Para posts existentes, apenas busque as duas listas em paralelo
      this.loadPostData(+id);
      
      this.servicoPost.getById(+id).subscribe({
        next: (resposta: Post) => {
          this.post = resposta;

          console.log (this.post.texto);
          console.log (this.post);
        }
      });

      if (id) {
    
    } else {
      // Para posts novos, apenas busque as tags disponíveis
      this.loadAvailableTags();
    }

      this.servicoTag.getTagByPost(+id).subscribe({
        next: (resposta: Tag[]) => {
          this.tagsList = resposta.map((tag, index) => {
            return {
              ...tag,
              tempId: index // Use o índice como tempId para tags existentes
            } as ITags;
          });
          console.log('Tags carregadas com tempId:', this.tagsList);
        
          if (this.tagsOptions.length > 0) {
            this.filterAvailableTags(); // Filtra as tags disponíveis
          }
        }
      }) 
    }

    this.subscription = this.servicoLogin.usuarioAutenticado.subscribe({
      next: (usuario: Usuario) => {
        this.usuarioLogado = usuario
      }
    })

    this.servicoCategoria.get().subscribe({
      next: (resposta: Categoria[]) => {
        this.categorias = resposta;
      }
    });
  }

  ngAfterViewInit(): void {
    // Adicionando o listener para o botão de categorias
    if (this.categoryButton) {
      this.statusListener = this.renderer.listen(this.categoryButton.nativeElement, 'click', (event) => {
        this.onCategoryClick();
        // this.initializeCategoryDropdownListener();
      });
    }

    // Adicionando o listener para o botão de tags
    if (this.tagButton) {
      this.tagListener = this.renderer.listen(this.tagButton.nativeElement, 'click', (event) => {
        this.onTagClick();
        // this.initializeTagDropdownListener();
      });
    }

    if (this.statusButton) {
      this.statusListener = this.renderer.listen(this.statusButton.nativeElement, 'click', (event) => {
        this.onStatusClick();
        // this.initializeTagDropdownListener();
      });
    }

    if (this.tagListen) {
      this.tagListen = this.renderer.listen(this.buttonTag.nativeElement, 'click', (event) => {
        this.onTag();
        // this.initializeTagDropdownListener();
      });
    }

    if (this.buttonOption) {
      this.optionListener = this.renderer.listen(this.buttonOption.nativeElement, 'click', (event) => {
        this.optionTagClick();
        console.log ("nfagksdgbsjkgb")
      })
    }

    if (this.capaInput) {
      this.capaListener = this.renderer.listen(this.capaInput.nativeElement, 'change', (event) => {
        console.log ("tem imagem de capa");
      })
    }
  }
  
  @ViewChild('editor') editor: any;
  
  editorText = '';

  changedEditor(event: EditorChangeContent | EditorChangeSelection) {
    console.log('Editor mudou ', event);
    this.editorText = event['editor']['root']['innerHTML'];

    this.countImageTags(this.editorText);
    // this.extractBase64Images(this.editorText);

    // console.log ('teste', this.editorText, "       ");
  }

  testarEnvio() {
    console.log('=== SIMULANDO ENVIO ===');
    
    // Processar as imagens (como seria no envio real)
    const result = this.extractBase64Images(this.post.texto);
    
    // Simular o que seria enviado
    // this.post.texto = result.modifiedContent;
    
    // console.log('Texto final:', this.post.texto);
    console.log('Imagens para envio:', this.images);
    console.log('Total de arquivos:', this.images.length);
  }

  private extractBase64Images(content: string): { modifiedContent: string, images: File[] } {
    const imgRegex = /<img[^>]*src=["'](data:image\/(png|jpeg|jpg);base64,([^"']+))["'][^>]*>/g;
    let modifiedContent = content;
    let match;
    let index = 0;
  
    this.images = [];

    while ((match = imgRegex.exec(content)) !== null) {
      const fullDataUrl = match[1];
      const mimeType = match[2]; // Tipo da imagem (png, jpeg, jpg)
      const base64Data = match[3]; // Dados base64 da imagem
  
      // Converter base64 para File
      const byteCharacters = atob(base64Data);
      const byteArray = new Uint8Array(byteCharacters.length);
      
      for (let i = 0; i < byteCharacters.length; i++) {
        byteArray[i] = byteCharacters.charCodeAt(i);
      }
      
      const blob = new Blob([byteArray], { type: `image/${mimeType}` });
      const file = new File([blob], `imagem_${index}.${mimeType}`, { type: `image/${mimeType}` });
  
      // Adiciona à lista global
      this.images.push(file);

      // Substituir no conteúdo
      modifiedContent = modifiedContent.replace(fullDataUrl, `FILE_${index}`);

      index++;
    }

    console.log('Imagens extraídas:', this.images);
    console.log('Texto modificado salvo em this.post.texto: ', modifiedContent);

    // Salvar texto modificado direto no post
    return { modifiedContent, images: this.images };
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

    this.convertToTags();

    // console.log (this.post.tags);

    const formData = new FormData();

    // Adiciona os campos do post
    formData.append('titulo', this.post.titulo);
    formData.append('status', this.post.status || EStatus.Publicado);

    if (this.post.id) formData.append('usuarioAlteraId', this.usuarioLogado?.id.toString());
    else formData.append('usuarioId', this.usuarioLogado?.id.toString());

    if (this.post.categoria?.id) formData.append('categoriaId', this.post.categoria.id.toString());

    if (this.post.legenda) formData.append('legenda', this.post.legenda);
  
    if (this.post.visibilidade) formData.append('visibilidade', this.post.visibilidade);

    if (this.post.publicacao) formData.append('publicacao', this.post.publicacao);

    if (this.post.data) formData.append('data', this.post.data);
    
    if (this.capaInput?.nativeElement?.files[0]) formData.append('imagemCapa', this.capaInput.nativeElement.files[0]);

    if (this.post.texto) {
      const result = this.extractBase64Images(this.post.texto);

      formData.append('texto', result.modifiedContent)
      
      if (result.images) {
        result.images.forEach((arquivo, index) => {
          formData.append('file', arquivo);
        });
      }
      
    }

    // Se tiver tags, adiciona cada uma
    if (this.post.tags && this.post.tags.length > 0) {
      // console.log('Tags originais:', this.post.tags);
  
      this.post.tags.forEach((tag, index) => {
        formData.append(`tags[${index}].id`, tag.id?.toString() || '');
        formData.append(`tags[${index}].nome`, tag.nome);
      });

      // console.log('Tags processadas:', this.post.tags);
    }
  
    this.servicoPost.save(formData, this.post.id).subscribe({
      complete: () => {
        this.servicoAlerta.enviarAlerta({
          tipo: ETipoAlerta.SUCESSO,
          mensagem: "Post salvo com sucesso"
        });
        console.log('Post salvo com sucesso')
        
        setTimeout(() => {
          this.router.navigate(['/view_posts']);
        }, 100); // tempo só pra deixar o alerta aparecer
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
    // console.log('Conteúdo salvo:', this.post.texto);
    // this.router.navigate(['/view_posts']);
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

  optionTagClick(): void {
    this.optionsButton = !this.optionsButton;
  }

  onTag(): void {
    this.filtersT = !this.filtersT;
  }

  editTag(tag: Tag): void {
    this.isEditing = true;
    this.editingTagId = tag.id;
    // Preenche o campo de input com o nome da tag atual
    this.newTag.nativeElement.value = tag.nome;
    // Foca no input para facilitar a edição
    this.newTag.nativeElement.focus();
  }

  cancelEditTag(): void {
    this.isEditing = false;
    this.editingTagId = null;
    this.newTag.nativeElement.value = '';
  }

  convertToTags(): void {
    this.post.tags = this.tagsList.map(tag => ({
      id: tag.id, // Mantém null para novas ou id para existentes
      nome: tag.nome
    }));
  }


  addTag(): void {
    const tagValue = this.newTag.nativeElement.value.trim();

    if (tagValue && tagValue !== '[]') {
      if (this.isEditing && this.editingTagId !== null) {
        // Modo de edição - apenas atualiza na tagsList
        const tagIndex = this.tagsList.findIndex(tag => tag.id === this.editingTagId);
        if (tagIndex !== -1) {
          // Atualiza o nome da tag
          this.tagsList[tagIndex].nome = tagValue;
          console.log('Tag atualizada na lista de tags:', this.tagsList[tagIndex]);
        }
        
        // Sai do modo de edição
        this.isEditing = false;
        this.editingTagId = null;
      } else {
        // Modo de adição (código existente)
        let newTag = this.addTagPost(tagValue);
        this.tagsList.push(newTag);
        console.log('Nova tag adicionada à lista de tags:', newTag);
      }
      
      // Limpa o input em ambos os casos
      this.newTag.nativeElement.value = '';
    } else {
      console.log('Valor de tag inválido');
    }
  }

  addTagPost(tagName: string): ITags {
    const maxTempId = Math.max(0, ...this.tagsList.map(t => t.tempId || 0));
    const newTempId = maxTempId + 1;

    return {
      id: null, // ID do banco será null para novas tags
      nome: tagName,
      tempId: newTempId // ID temporário para manipulação no frontend
    };
  }

  createTag(id: number | null, nome: string): ITags {
    return {
      id: id,
      nome,
      tempId: -(this.tagsList.filter(t => t.tempId !== undefined).length + 1)
    };
  }

  removeTag(tag: ITags): void {
    // Remove a tag da lista de tags do post atual
    if (this.tagsList) {
      this.tagsList = this.tagsList.filter(t => t.tempId !== tag.tempId);
      
      if (tag.id !== null) {
        // Verifica se a tag já não está na lista de opções para evitar duplicatas
        const tagJaExiste = this.tagsOptions.some(t => t.id === tag.id);
        
        if (!tagJaExiste) {
          this.tagsOptions.push({
            id: tag.id,
            nome: tag.nome
          });
        }
      }

      console.log('Tag removida. Tags restantes:', this.tagsList);
    }
  }

  addTagList(tag: Tag): void {
    // Verifica se a tag já existe na lista atual
    const tagExistente = this.tagsList.find(
      t => t.nome.toLowerCase() === tag.nome.toLowerCase()
    );
  
    // Se a tag não existir, adiciona
    if (!tagExistente) {
      this.tagsOptions = this.tagsOptions.filter(t => t.id !== tag.id);
      // Usa o método createTag para criar uma nova ITags a partir da tag selecionada
      const novaTag = this.createTag(tag.id, tag.nome);
      
      // Adiciona à lista de tags do post
      this.tagsList.push(novaTag);
      
      console.log('Tag adicionada da lista de opções:', novaTag);
    } else {
      console.log('Tag já existe na lista');
    }
  }

  public publicacaoOptions = Object.keys(EPublicacao)
    .filter(key => isNaN(Number(key))) // Filtra apenas as chaves, ignorando os números
    .map(key => ({ label: key, value: EPublicacao[key as keyof typeof EPublicacao] }));

  public statusOptions = Object.keys(EStatus)
    .filter(key => isNaN(Number(key))) // Filtra apenas as chaves, ignorando os números
    .map(key => ({ label: key, value: EStatus[key as keyof typeof EStatus] }));

  public visibilidadeOptions = Object.keys(EVisibilidade)
    .filter(key => isNaN(Number(key))) // Filtra apenas as chaves, ignorando os números
    .map(key => ({ label: key, value: EVisibilidade[key as keyof typeof EVisibilidade] }));

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

  confirmarVisibilidade(): void {
    // console.log ('entrei')
    if (this.selectedVisibilidade) {
      this.post.visibilidade = this.selectedVisibilidade;
      this.visibilidadeContent = true;
      // console.log ('entrei dnv')
    }
    // } else {
    //   alert('Selecione uma visibilidade');
    // }
  }

  confirmarPublicacao(): void {
    if (this.selectedPublicacao) {
      this.post.publicacao = this.selectedPublicacao;
      this.publicacaoContent = true;
      // console.log ('entrei dnv')
    }
  }

  toggleVisibilidade() {
    if (this.visibilidadeContent) {
      this.selectedVisibilidade = this.post.visibilidade ?? 'PUBLICO';
    }
    this.visibilidadeContent = !this.visibilidadeContent;
  }

  togglePublicacao() {
    // this.selectedPublicacao = this.post.publicacao;
    if (this.publicacaoContent) {
      this.selectedPublicacao = this.post.publicacao ?? "IMEDIATA";
    }

    this.publicacaoContent = !this.publicacaoContent;
  }

  onDateTimeChange(event: Event) {
    const input = event.target as HTMLInputElement;
    const selectedDateTime = input.value; 

    if (selectedDateTime) {
      this.post.data = selectedDateTime;
      console.log ('Data selecionada: ', this.post.data);
    }
  }

  private loadPostData(postId: number): void {
    let tagsLoaded = false;
    let optionsLoaded = false;

    // Buscar tags do post
    this.servicoTag.getTagByPost(postId).subscribe({
      next: (resposta: Tag[]) => {
        this.tagsList = resposta.map((tag, index) => ({
          ...tag,
          tempId: index
        } as ITags));
        
        tagsLoaded = true;
        if (optionsLoaded) {
          this.filterAvailableTags();
        }
      }
    });

    // Buscar todas as tags
    this.servicoTag.get().subscribe({
      next: (resposta: Tag[]) => {
        this.tagsOptions = resposta;
        
        optionsLoaded = true;
        if (tagsLoaded) {
          this.filterAvailableTags();
        }
      }
    });
  }

  private loadAvailableTags(): void {
    this.servicoTag.get().subscribe({
      next: (resposta: Tag[]) => {
        this.tagsOptions = resposta;
        // Para posts novos, não precisa filtrar
      }
    });
  }

  filterAvailableTags(): void {
    if (!this.tagsOptions || this.tagsOptions.length === 0) {
      console.log('Nenhuma tag disponível no sistema');
      return;
    }

    if (!this.tagsList || this.tagsList.length === 0) {
      console.log('Novo post: todas as tags estão disponíveis');
      // ✅ Não filtra nada, mantém todas disponíveis
      return;
    }
    
    // Filtra apenas as tags que NÃO estão no post atual
    this.tagsOptions = this.tagsOptions.filter(option => 
      !this.tagsList.some(postTag => postTag.id === option.id)
    );
    
    console.log('Tags disponíveis após filtro:', this.tagsOptions);
  }

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
