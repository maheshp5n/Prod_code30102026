import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatAccountsdetailComponent } from './bat-accountsdetail.component';

describe('BatAccountsdetailComponent', () => {
  let component: BatAccountsdetailComponent;
  let fixture: ComponentFixture<BatAccountsdetailComponent>;

  beforeEach(async(() => {
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
