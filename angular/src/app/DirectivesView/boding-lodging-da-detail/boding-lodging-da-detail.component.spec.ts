import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BodingLodgingDaDetailComponent } from './boding-lodging-da-detail.component';

describe('BodingLodgingDaDetailComponent', () => {
  let component: BodingLodgingDaDetailComponent;
  let fixture: ComponentFixture<BodingLodgingDaDetailComponent>;

  beforeEach(async(() => {
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
