import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatCreateCircleComponent } from './bat-create-circle.component';

describe('BatCreateCircleComponent', () => {
  let component: BatCreateCircleComponent;
  let fixture: ComponentFixture<BatCreateCircleComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatCreateCircleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatCreateCircleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
