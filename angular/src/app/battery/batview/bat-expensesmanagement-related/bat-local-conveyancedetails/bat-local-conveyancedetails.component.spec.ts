import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatLocalConveyancedetailsComponent } from './bat-local-conveyancedetails.component';

describe('BatLocalConveyancedetailsComponent', () => {
  let component: BatLocalConveyancedetailsComponent;
  let fixture: ComponentFixture<BatLocalConveyancedetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatLocalConveyancedetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatLocalConveyancedetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
