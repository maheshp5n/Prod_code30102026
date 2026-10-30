import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatTourdetailsComponent } from './bat-tourdetails.component';

describe('BatTourdetailsComponent', () => {
  let component: BatTourdetailsComponent;
  let fixture: ComponentFixture<BatTourdetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BatTourdetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatTourdetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
