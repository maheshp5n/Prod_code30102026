import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatServiceorderdetailsComponent } from './bat-serviceorderdetails.component';

describe('BatServiceorderdetailsComponent', () => {
  let component: BatServiceorderdetailsComponent;
  let fixture: ComponentFixture<BatServiceorderdetailsComponent>;

  beforeEach(async(() => {
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
