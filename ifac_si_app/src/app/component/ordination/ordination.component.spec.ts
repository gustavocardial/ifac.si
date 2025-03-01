import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrdinationComponent } from './ordination.component';

describe('OrdinationComponent', () => {
  let component: OrdinationComponent;
  let fixture: ComponentFixture<OrdinationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OrdinationComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(OrdinationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
