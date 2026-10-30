import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApprovedlistComponent } from './approvedlist.component';

describe('ApprovedlistComponent', () => {
  let component: ApprovedlistComponent;
  let fixture: ComponentFixture<ApprovedlistComponent>;

  beforeEach(async(() => {
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
