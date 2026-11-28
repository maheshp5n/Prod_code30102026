import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatServiceorderdetailsComponent } from './bat-serviceorderdetails.component';

describe('BatServiceorderdetailsComponent', () => {
  let component: BatServiceorderdetailsComponent;
  let fixture: ComponentFixture<BatServiceorderdetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatServiceorderdetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatServiceorderdetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
