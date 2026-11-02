import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CreateProoveOfDeliveryComponent } from './create-proove-of-delivery.component';

describe('CreateProoveOfDeliveryComponent', () => {
  let component: CreateProoveOfDeliveryComponent;
  let fixture: ComponentFixture<CreateProoveOfDeliveryComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateProoveOfDeliveryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateProoveOfDeliveryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
