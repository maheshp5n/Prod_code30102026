import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceOrderReturnComponent } from './service-order-return.component';

describe('ServiceOrderReturnComponent', () => {
  let component: ServiceOrderReturnComponent;
  let fixture: ComponentFixture<ServiceOrderReturnComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ServiceOrderReturnComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ServiceOrderReturnComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
