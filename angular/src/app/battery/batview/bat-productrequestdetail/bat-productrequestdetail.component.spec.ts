import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatProductrequestdetailComponent } from './bat-productrequestdetail.component';

describe('BatProductrequestdetailComponent', () => {
  let component: BatProductrequestdetailComponent;
  let fixture: ComponentFixture<BatProductrequestdetailComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatProductrequestdetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatProductrequestdetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
