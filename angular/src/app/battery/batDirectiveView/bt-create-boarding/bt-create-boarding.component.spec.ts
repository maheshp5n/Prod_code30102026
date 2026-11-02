import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BtCreateBoardingComponent } from './bt-create-boarding.component';

describe('BtCreateBoardingComponent', () => {
  let component: BtCreateBoardingComponent;
  let fixture: ComponentFixture<BtCreateBoardingComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BtCreateBoardingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtCreateBoardingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
