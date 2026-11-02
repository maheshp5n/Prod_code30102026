import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatServiceorderdetailsViewComponent } from './bat-serviceorderdetails-view.component';

describe('BatServiceorderdetailsViewComponent', () => {
  let component: BatServiceorderdetailsViewComponent;
  let fixture: ComponentFixture<BatServiceorderdetailsViewComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatServiceorderdetailsViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatServiceorderdetailsViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
