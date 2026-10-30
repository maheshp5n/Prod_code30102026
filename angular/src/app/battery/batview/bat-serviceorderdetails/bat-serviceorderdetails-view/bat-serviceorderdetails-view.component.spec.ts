import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatServiceorderdetailsViewComponent } from './bat-serviceorderdetails-view.component';

describe('BatServiceorderdetailsViewComponent', () => {
  let component: BatServiceorderdetailsViewComponent;
  let fixture: ComponentFixture<BatServiceorderdetailsViewComponent>;

  beforeEach(async(() => {
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
