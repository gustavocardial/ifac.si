import { Component, OnInit } from '@angular/core';
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
handleFilterClick(arg0: number) {
throw new Error('Method not implemented.');
}
  constructor (
    private servicoCategoria : CategoriaService,
    private servicoTag : TagService
  ) {}

  categorias: Categoria[] = Array<Categoria>();
  tags: Tag[] = Array<Tag>();

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
}
