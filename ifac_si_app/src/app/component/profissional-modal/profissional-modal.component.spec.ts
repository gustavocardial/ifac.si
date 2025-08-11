import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfissionalModalComponent } from './profissional-modal.component';

describe('ProfissionalModalComponent', () => {
  let component: ProfissionalModalComponent;
  let fixture: ComponentFixture<ProfissionalModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProfissionalModalComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ProfissionalModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
