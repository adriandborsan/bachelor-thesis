import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReactiveLogComponent } from './reactive-log.component';

describe('ReactiveLogComponent', () => {
  let component: ReactiveLogComponent;
  let fixture: ComponentFixture<ReactiveLogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReactiveLogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReactiveLogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
