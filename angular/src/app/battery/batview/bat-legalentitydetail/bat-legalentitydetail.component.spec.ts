import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatLegalentitydetailComponent } from './bat-legalentitydetail.component';

describe('BatLegalentitydetailComponent', () => {
  let component: BatLegalentitydetailComponent;
  let fixture: ComponentFixture<BatLegalentitydetailComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatLegalentitydetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatLegalentitydetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
