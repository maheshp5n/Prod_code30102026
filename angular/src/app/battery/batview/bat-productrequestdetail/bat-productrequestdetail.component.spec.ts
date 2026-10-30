import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatProductrequestdetailComponent } from './bat-productrequestdetail.component';

describe('BatProductrequestdetailComponent', () => {
  let component: BatProductrequestdetailComponent;
  let fixture: ComponentFixture<BatProductrequestdetailComponent>;

  beforeEach(async(() => {
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
