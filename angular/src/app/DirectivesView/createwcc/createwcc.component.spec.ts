import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreatewccComponent } from './createwcc.component';

describe('CreatewccComponent', () => {
  let component: CreatewccComponent;
  let fixture: ComponentFixture<CreatewccComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreatewccComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreatewccComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
