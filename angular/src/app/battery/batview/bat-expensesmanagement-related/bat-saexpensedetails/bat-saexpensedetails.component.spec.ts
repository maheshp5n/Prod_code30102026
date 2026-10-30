import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatSAExpensedetailsComponent } from './bat-saexpensedetails.component';

describe('BatSAExpensedetailsComponent', () => {
  let component: BatSAExpensedetailsComponent;
  let fixture: ComponentFixture<BatSAExpensedetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BatSAExpensedetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatSAExpensedetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
