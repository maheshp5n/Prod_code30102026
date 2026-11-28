import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BtCreateCircleComponent } from './bt-create-circle.component';

describe('BtCreateCircleComponent', () => {
  let component: BtCreateCircleComponent;
  let fixture: ComponentFixture<BtCreateCircleComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BtCreateCircleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtCreateCircleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
