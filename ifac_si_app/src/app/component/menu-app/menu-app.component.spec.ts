import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MenuAppComponent } from './menu-app.component';

describe('MenuAppComponent', () => {
  let component: MenuAppComponent;
  let fixture: ComponentFixture<MenuAppComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MenuAppComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MenuAppComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
