import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateServiceAppoinmentBycaseComponent } from './create-service-appoinment-bycase.component';

describe('CreateServiceAppoinmentBycaseComponent', () => {
  let component: CreateServiceAppoinmentBycaseComponent;
  let fixture: ComponentFixture<CreateServiceAppoinmentBycaseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateServiceAppoinmentBycaseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateServiceAppoinmentBycaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
