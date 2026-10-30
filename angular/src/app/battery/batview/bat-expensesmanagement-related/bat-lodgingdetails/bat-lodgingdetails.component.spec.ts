import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatLodgingdetailsComponent } from './bat-lodgingdetails.component';

describe('BatLodgingdetailsComponent', () => {
  let component: BatLodgingdetailsComponent;
  let fixture: ComponentFixture<BatLodgingdetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BatLodgingdetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatLodgingdetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
