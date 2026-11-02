import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CreateServiceAppoinmentBycaseComponent } from './create-service-appoinment-bycase.component';

describe('CreateServiceAppoinmentBycaseComponent', () => {
  let component: CreateServiceAppoinmentBycaseComponent;
  let fixture: ComponentFixture<CreateServiceAppoinmentBycaseComponent>;

  beforeEach(waitForAsync(() => {
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
