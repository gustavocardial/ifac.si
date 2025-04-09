import { Component, ElementRef, OnInit, ViewChild, Renderer2, AfterViewInit, Output, EventEmitter } from '@angular/core';
import { CategoriaService } from '../../service/categoria.service';
import { TagService } from '../../service/tag.service';
import { Categoria } from '../../model/categoria';
import { Tag } from '../../model/tag';

@Component({
  selector: 'app-filters',
  templateUrl: './filters.component.html',
  styleUrl: './filters.component.css'
})
export class FiltersComponent implements OnInit, AfterViewInit{
  @ViewChild('category') categoryButton!: ElementRef;
  @ViewChild('tag') tagButton!: ElementRef;

  @ViewChild('categorysApp', { static: false }) categoryDropDown!: ElementRef;
  @ViewChild('tagsApp', { static: false }) tagDropDown!: ElementRef;

  @Output() catFilter = new EventEmitter<number | null>();
  @Output() tagFilter = new EventEmitter<string | null>();
  @Output() ordemMudou = new EventEmitter<string>();
  @Output() nonSelectFilter = new EventEmitter();

  categorias: Categoria[] = Array<Categoria>();
  tags: Tag[] = Array<Tag>();
  filtersC: boolean = false;
  filtersT: boolean = false;
  selectedCategoryId: number | null = null; // Para controlar a categoria selecionada
  selectedTagName: string | null = null; // Para controlar a tag selecionada  

  private categoryListener: (() => void) | undefined;
  private tagListener: (() => void) | undefined;
  private catListen: (() => void) | undefined;
  private tagListen: (() => void) | undefined;

  constructor (
    private servicoCategoria : CategoriaService,
    private servicoTag : TagService,
    private renderer: Renderer2
  ) {}
  
  ngOnInit(): void {
    this.servicoCategoria.get().subscribe({
      next: (resposta: Categoria[]) => {
        this.categorias = resposta;
        // console.log('Categorias:', this.categorias);
      }
    });

    this.servicoTag.get().subscribe({
      next: (resposta: Tag[]) => {
        // this.tags = resposta;
        this.tags = resposta.map(tag => ({
          id: tag.id,
          nome: tag.nome
        }));
        // console.log('Tags:', this.tags); 
      }
    });  
  }

  ngAfterViewInit(): void {
    // Adicionando o listener para o botão de categorias
    this.categoryListener = this.renderer.listen(this.categoryButton.nativeElement, 'click', (event) => {
      this.onCategoryClick();
      this.initializeCategoryDropdownListener();
    });

    // Adicionando o listener para o botão de tags
    this.tagListener = this.renderer.listen(this.tagButton.nativeElement, 'click', (event) => {
      this.onTagClick();
      this.initializeTagDropdownListener();
    });
  }

  //Começar a fazer filtro funcionar, mandar propriedades para busca desse component para outro

  handleFilterCat(idCat: number | null): void {
    console.log(idCat);
    if (this.selectedCategoryId === idCat) {
      // Se já estiver selecionado, desmarque
      this.selectedCategoryId = null;
      this.catFilter.emit(null);
    } else {
      // Caso contrário, marque
      this.selectedCategoryId = idCat;
      this.catFilter.emit(idCat);
    }

    this.selectNon();
  }

  handleFilterTag(idNome: string | null): void {
    if (this.selectedTagName === idNome) {
      // Se já estiver selecionado, desmarque
      this.selectedTagName = null;
      this.tagFilter.emit(null);
    } else {
      // Caso contrário, marque
      this.selectedTagName = idNome;
      this.tagFilter.emit(idNome);
    }

    console.log (idNome);
    this.selectNon();
  }

  selectNon(): void {
    if (this.selectedCategoryId == null && this.selectedTagName == null) this.nonSelectFilter.emit();
  }


  // Função chamada ao clicar no botão de categoria
  onCategoryClick(): void {
    this.filtersC = !this.filtersC;
  }

  // Função chamada ao clicar no botão de tag
  onTagClick(): void {
    this.filtersT = !this.filtersT;
  }

  // Removendo listeners ao destruir o componente
  ngOnDestroy(): void {
    if (this.categoryListener) {
      this.categoryListener();
    }
    if (this.tagListener) {
      this.tagListener();
    }

    if (this.catListen) {
      this.catListen();
    }
    if (this.tagListen) {
      this.tagListen();
    }
  }

  private initializeCategoryDropdownListener(): void {
    if (this.categoryDropDown && !this.catListen) {
      this.catListen = this.renderer.listen(this.categoryDropDown.nativeElement, 'blur', () => {
        this.onCategoryClick();
      });
    }
  }

  private initializeTagDropdownListener(): void {
    if (this.tagDropDown && !this.tagListen) {
      this.tagListen = this.renderer.listen(this.tagDropDown.nativeElement, 'blur', () => {
        this.onTagClick();
      });
    }
  }

  atualizarOrdenacao(valor: string) {
    this.ordemMudou.emit(valor); // Repassa o evento para o componente pai
  }
}
