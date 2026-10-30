import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BtCreateCircleComponent } from './bt-create-circle.component';

describe('BtCreateCircleComponent', () => {
  let component: BtCreateCircleComponent;
  let fixture: ComponentFixture<BtCreateCircleComponent>;

  beforeEach(async(() => {
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
