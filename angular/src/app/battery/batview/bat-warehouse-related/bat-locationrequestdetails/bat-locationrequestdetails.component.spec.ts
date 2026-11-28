import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatLocationrequestdetailsComponent } from './bat-locationrequestdetails.component';

describe('BatLocationrequestdetailsComponent', () => {
  let component: BatLocationrequestdetailsComponent;
  let fixture: ComponentFixture<BatLocationrequestdetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatLocationrequestdetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatLocationrequestdetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
