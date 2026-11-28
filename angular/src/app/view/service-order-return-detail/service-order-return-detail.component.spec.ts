import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ServiceOrderReturnDetailComponent } from './service-order-return-detail.component';

describe('ServiceOrderReturnDetailComponent', () => {
  let component: ServiceOrderReturnDetailComponent;
  let fixture: ComponentFixture<ServiceOrderReturnDetailComponent>;

  beforeEach(waitForAsync(() => {
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
