import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostReadComponent } from './post-read.component';

describe('PostReadComponent', () => {
  let component: PostReadComponent;
  let fixture: ComponentFixture<PostReadComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PostReadComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PostReadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
