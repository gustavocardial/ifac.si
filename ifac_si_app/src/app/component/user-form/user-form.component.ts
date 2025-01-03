import { AfterViewInit, Component, ElementRef, EventEmitter, Output, Renderer2, ViewChild } from '@angular/core';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrl: './user-form.component.css'
})
export class UserFormComponent implements AfterViewInit{
  constructor(private renderer: Renderer2) {}

  @Output() cancelForm = new EventEmitter;
  @ViewChild('cancelButton') cancelButton!: ElementRef;

  ngAfterViewInit() {
    if (this.cancelButton && this.cancelButton.nativeElement) {
      this.renderer.listen(this.cancelButton.nativeElement, 'click', () => {
        this.cancelForm.emit();
      });
    }
  }
}
