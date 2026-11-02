import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatLocationproductdetailsComponent } from './bat-locationproductdetails.component';

describe('BatLocationproductdetailsComponent', () => {
  let component: BatLocationproductdetailsComponent;
  let fixture: ComponentFixture<BatLocationproductdetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatLocationproductdetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatLocationproductdetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
