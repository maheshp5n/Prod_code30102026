import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatAccountsdetailComponent } from './bat-accountsdetail.component';

describe('BatAccountsdetailComponent', () => {
  let component: BatAccountsdetailComponent;
  let fixture: ComponentFixture<BatAccountsdetailComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatAccountsdetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatAccountsdetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
