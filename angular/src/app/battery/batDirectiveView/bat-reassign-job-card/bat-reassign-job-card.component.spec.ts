import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatReassignJobCardComponent } from './bat-reassign-job-card.component';

describe('BatReassignJobCardComponent', () => {
  let component: BatReassignJobCardComponent;
  let fixture: ComponentFixture<BatReassignJobCardComponent>;

  beforeEach(async(() => {
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
