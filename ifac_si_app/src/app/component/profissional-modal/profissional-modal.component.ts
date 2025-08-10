import { AfterViewInit, Component, ElementRef, EventEmitter, Input, OnInit, Output, Renderer2, ViewChild } from '@angular/core';
import { Profissional } from '../../model/profissional';

@Component({
  selector: 'app-profissional-modal',
  templateUrl: './profissional-modal.component.html',
  styleUrl: './profissional-modal.component.css'
})
export class ProfissionalModalComponent implements AfterViewInit, OnInit{
  constructor(
    private renderer: Renderer2
  ) {}

  @Input() profissionalId?: number;

  profissional: Profissional = <Profissional>{};

  funcao: string = '';
  titulo: string = '';

  @ViewChild('cancelButton') cancelButton!: ElementRef;
  @ViewChild('confirmButton') confirmButton!: ElementRef;

  @Output() cancelForm = new EventEmitter;
  @Output() confirmForm = new EventEmitter;
  
  ngAfterViewInit() {
    if (this.cancelButton && this.cancelButton.nativeElement) {
      this.renderer.listen(this.cancelButton.nativeElement, 'click', () => {
        this.cancelForm.emit();
      });
    }

    if (this.confirmButton && this.confirmButton.nativeElement) {
      this.renderer.listen(this.confirmButton.nativeElement, 'click', () => {
        this.confirmForm.emit();
      } )
    }
  }

  ngOnInit(): void {
      if (this.profissionalId != null){
        // Lógica para atualizar dados de profissional
      } else {
        this.profissional = <Profissional>{};
        this.funcao = '';
        this.titulo = '';
      }
  }
}
