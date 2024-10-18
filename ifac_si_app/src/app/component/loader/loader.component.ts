import { Component } from '@angular/core';
import { LoaderService } from '../../service/loader.service';

@Component({
  selector: 'app-loader',
  templateUrl: './loader.component.html',
  styleUrl: './loader.component.css'
})
export class LoaderComponent {
  loading: boolean = false;

  constructor(private servico: LoaderService) {
    this.servico.isLoading.subscribe(valor => {
      this.loading = valor;
      // console.log(valor)
    });
  }
}
