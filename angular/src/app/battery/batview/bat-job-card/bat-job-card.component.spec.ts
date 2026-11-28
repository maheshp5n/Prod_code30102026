import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatJobCardComponent } from './bat-job-card.component';

describe('BatJobCardComponent', () => {
  let component: BatJobCardComponent;
  let fixture: ComponentFixture<BatJobCardComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatJobCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatJobCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
