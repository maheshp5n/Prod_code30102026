import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateSSAComponent } from './create-ssa.component';

describe('CreateSSAComponent', () => {
  let component: CreateSSAComponent;
  let fixture: ComponentFixture<CreateSSAComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateSSAComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateSSAComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
