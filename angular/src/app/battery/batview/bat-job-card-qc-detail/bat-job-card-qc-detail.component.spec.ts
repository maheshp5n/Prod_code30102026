import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatJobCardQcDetailComponent } from './bat-job-card-qc-detail.component';

describe('BatJobCardQcDetailComponent', () => {
  let component: BatJobCardQcDetailComponent;
  let fixture: ComponentFixture<BatJobCardQcDetailComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatJobCardQcDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatJobCardQcDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
