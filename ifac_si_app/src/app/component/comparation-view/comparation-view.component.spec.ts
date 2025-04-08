import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ComparationViewComponent } from './comparation-view.component';

describe('ComparationViewComponent', () => {
  let component: ComparationViewComponent;
  let fixture: ComponentFixture<ComparationViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ComparationViewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ComparationViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
