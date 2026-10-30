import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BtCreateaccountComponent } from './bt-createaccount.component';

describe('BtCreateaccountComponent', () => {
  let component: BtCreateaccountComponent;
  let fixture: ComponentFixture<BtCreateaccountComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BtCreateaccountComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtCreateaccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
