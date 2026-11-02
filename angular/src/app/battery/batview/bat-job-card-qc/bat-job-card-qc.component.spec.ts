import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatJobCardQcComponent } from './bat-job-card-qc.component';

describe('BatJobCardQcComponent', () => {
  let component: BatJobCardQcComponent;
  let fixture: ComponentFixture<BatJobCardQcComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatJobCardQcComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatJobCardQcComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
