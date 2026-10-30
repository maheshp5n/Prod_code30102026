import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatJobCardQcDetailComponent } from './bat-job-card-qc-detail.component';

describe('BatJobCardQcDetailComponent', () => {
  let component: BatJobCardQcDetailComponent;
  let fixture: ComponentFixture<BatJobCardQcDetailComponent>;

  beforeEach(async(() => {
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
