import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginationHandleComponent } from './pagination-handle.component';

describe('PaginationHandleComponent', () => {
  let component: PaginationHandleComponent;
  let fixture: ComponentFixture<PaginationHandleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PaginationHandleComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PaginationHandleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
