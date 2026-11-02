import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ApprovedlistComponent } from './approvedlist.component';

describe('ApprovedlistComponent', () => {
  let component: ApprovedlistComponent;
  let fixture: ComponentFixture<ApprovedlistComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ApprovedlistComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApprovedlistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
