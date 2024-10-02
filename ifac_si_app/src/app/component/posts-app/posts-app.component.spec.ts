import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostsAppComponent } from './posts-app.component';

describe('PostsAppComponent', () => {
  let component: PostsAppComponent;
  let fixture: ComponentFixture<PostsAppComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PostsAppComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PostsAppComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
