import { Component, ElementRef, OnInit, ViewChild, Renderer2 } from '@angular/core';
import { CategoriaService } from '../../service/categoria.service';
import { TagService } from '../../service/tag.service';
import { Categoria } from '../../model/categoria';
import { Tag } from '../../model/tag';

@Component({
  selector: 'app-filters',
  templateUrl: './filters.component.html',
  styleUrl: './filters.component.css'
})
export class FiltersComponent implements OnInit{
  @ViewChild('category') categoryButton!: ElementRef;
  @ViewChild('tag') tagButton!: ElementRef;

  categorias: Categoria[] = Array<Categoria>();
  tags: Tag[] = Array<Tag>();
  filtersC: boolean = false;
  filtersT: boolean = false;

  private categoryListener: (() => void) | undefined;
  private tagListener: (() => void) | undefined;

  constructor (
    private servicoCategoria : CategoriaService,
    private servicoTag : TagService,
    private renderer: Renderer2
  ) {}
  
  ngOnInit(): void {
    this.servicoCategoria.get().subscribe({
      next: (resposta: Categoria[]) => {
        this.categorias = resposta;
      }
    });

    this.servicoTag.get().subscribe({
      next: (resposta: Tag[]) => {
        this.tags = resposta;
      }
    });  
  }

  ngAfterViewInit(): void {
    // Adicionando o listener para o botão de categorias
    this.categoryListener = this.renderer.listen(this.categoryButton.nativeElement, 'click', (event) => {
      this.onCategoryClick();
    });

    // Adicionando o listener para o botão de tags
    this.tagListener = this.renderer.listen(this.tagButton.nativeElement, 'click', (event) => {
      this.onTagClick();
    });
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
  }

  handleFilterClick(arg0: number) {
  throw new Error('Method not implemented.');
  }
}
