import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BodingLodgingDaDetailComponent } from './boding-lodging-da-detail.component';

describe('BodingLodgingDaDetailComponent', () => {
  let component: BodingLodgingDaDetailComponent;
  let fixture: ComponentFixture<BodingLodgingDaDetailComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BodingLodgingDaDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BodingLodgingDaDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
