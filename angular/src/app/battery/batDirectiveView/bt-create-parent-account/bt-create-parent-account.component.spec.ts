import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BtCreateParentAccountComponent } from './bt-create-parent-account.component';

describe('BtCreateParentAccountComponent', () => {
  let component: BtCreateParentAccountComponent;
  let fixture: ComponentFixture<BtCreateParentAccountComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BtCreateParentAccountComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtCreateParentAccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
