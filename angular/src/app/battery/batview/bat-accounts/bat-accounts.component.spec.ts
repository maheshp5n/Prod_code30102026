import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatAccountsComponent } from './bat-accounts.component';

describe('BatAccountsComponent', () => {
  let component: BatAccountsComponent;
  let fixture: ComponentFixture<BatAccountsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BatAccountsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatAccountsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
