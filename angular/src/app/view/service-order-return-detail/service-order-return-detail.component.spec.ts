import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceOrderReturnDetailComponent } from './service-order-return-detail.component';

describe('ServiceOrderReturnDetailComponent', () => {
  let component: ServiceOrderReturnDetailComponent;
  let fixture: ComponentFixture<ServiceOrderReturnDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ServiceOrderReturnDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ServiceOrderReturnDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
