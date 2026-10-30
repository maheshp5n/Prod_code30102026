import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateSystemconfigurationparameterComponent } from './create-systemconfigurationparameter.component';

describe('CreateSystemconfigurationparameterComponent', () => {
  let component: CreateSystemconfigurationparameterComponent;
  let fixture: ComponentFixture<CreateSystemconfigurationparameterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateSystemconfigurationparameterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateSystemconfigurationparameterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
