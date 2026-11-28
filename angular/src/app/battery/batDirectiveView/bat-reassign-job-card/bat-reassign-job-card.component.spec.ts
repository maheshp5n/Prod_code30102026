import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatReassignJobCardComponent } from './bat-reassign-job-card.component';

describe('BatReassignJobCardComponent', () => {
  let component: BatReassignJobCardComponent;
  let fixture: ComponentFixture<BatReassignJobCardComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatReassignJobCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatReassignJobCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
