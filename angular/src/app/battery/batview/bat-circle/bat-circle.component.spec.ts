import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatCircleComponent } from './bat-circle.component';

describe('BatCircleComponent', () => {
  let component: BatCircleComponent;
  let fixture: ComponentFixture<BatCircleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BatCircleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatCircleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
