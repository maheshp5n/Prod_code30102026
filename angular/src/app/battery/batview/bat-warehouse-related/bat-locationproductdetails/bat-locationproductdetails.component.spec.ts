import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatLocationproductdetailsComponent } from './bat-locationproductdetails.component';

describe('BatLocationproductdetailsComponent', () => {
  let component: BatLocationproductdetailsComponent;
  let fixture: ComponentFixture<BatLocationproductdetailsComponent>;

  beforeEach(async(() => {
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
