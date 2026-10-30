import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatCircleDetailComponent } from './bat-circle-detail.component';

describe('BatCircleDetailComponent', () => {
  let component: BatCircleDetailComponent;
  let fixture: ComponentFixture<BatCircleDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BatCircleDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatCircleDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
