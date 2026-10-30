import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RequisitionindetailsComponent } from './requisitionindetails.component';

describe('RequisitionindetailsComponent', () => {
  let component: RequisitionindetailsComponent;
  let fixture: ComponentFixture<RequisitionindetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RequisitionindetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RequisitionindetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
