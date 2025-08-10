import { AfterViewInit, Component, ElementRef, EventEmitter, Output, Renderer2, ViewChild } from '@angular/core';

@Component({
  selector: 'app-profissional-modal',
  templateUrl: './profissional-modal.component.html',
  styleUrl: './profissional-modal.component.css'
})
export class ProfissionalModalComponent implements AfterViewInit{
  constructor(
    private renderer: Renderer2
  ) {}

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
}
