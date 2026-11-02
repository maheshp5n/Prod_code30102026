import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatServiceorderdetailsRelatedComponent } from './bat-serviceorderdetails-related.component';

describe('BatServiceorderdetailsRelatedComponent', () => {
  let component: BatServiceorderdetailsRelatedComponent;
  let fixture: ComponentFixture<BatServiceorderdetailsRelatedComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatServiceorderdetailsRelatedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatServiceorderdetailsRelatedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
