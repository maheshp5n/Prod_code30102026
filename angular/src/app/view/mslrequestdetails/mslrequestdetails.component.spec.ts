import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MslrequestdetailsComponent } from './mslrequestdetails.component';

describe('MslrequestdetailsComponent', () => {
  let component: MslrequestdetailsComponent;
  let fixture: ComponentFixture<MslrequestdetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MslrequestdetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MslrequestdetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
