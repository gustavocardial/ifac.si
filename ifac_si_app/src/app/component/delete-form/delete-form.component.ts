import { AfterViewInit, Component, ElementRef, EventEmitter, Input, Output, Renderer2, ViewChild } from '@angular/core';
import { PostService } from '../../service/post.service';

@Component({
  selector: 'app-delete-form',
  templateUrl: './delete-form.component.html',
  styleUrl: './delete-form.component.css'
})
export class DeleteFormComponent implements AfterViewInit{
  constructor(
    private renderer: Renderer2,
    private servico: PostService
  ) {}

  @ViewChild('cancelButton') cancelButton!: ElementRef;
  @ViewChild('confirmButton') confirmButton!: ElementRef;

  @Output() cancelForm = new EventEmitter;
  @Output() confirmForm = new EventEmitter;
  @Input() messageLine1: string = 'Você tem certeza que deseja continuar com esta ação?';
  @Input() messageLine2?: string;
  @Input() mostrarCampoTexto: boolean = false;

  mensagemReprova: string = '';
  
  ngAfterViewInit() {
    if (this.cancelButton && this.cancelButton.nativeElement) {
      this.renderer.listen(this.cancelButton.nativeElement, 'click', () => {
        this.cancelForm.emit();
      });
    }

    if (this.confirmButton && this.confirmButton.nativeElement) {
      this.renderer.listen(this.confirmButton.nativeElement, 'click', () => {
        this.confirmForm.emit(this.mostrarCampoTexto ? this.mensagemReprova : '');
      } )
    }
  }
}
