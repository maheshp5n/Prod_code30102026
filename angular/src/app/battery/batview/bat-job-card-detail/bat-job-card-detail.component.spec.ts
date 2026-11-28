import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatJobCardDetailComponent } from './bat-job-card-detail.component';

describe('BatJobCardDetailComponent', () => {
  let component: BatJobCardDetailComponent;
  let fixture: ComponentFixture<BatJobCardDetailComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatJobCardDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatJobCardDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
