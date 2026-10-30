import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PricebookdetailsComponent } from './pricebookdetails.component';

describe('PricebookdetailsComponent', () => {
  let component: PricebookdetailsComponent;
  let fixture: ComponentFixture<PricebookdetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PricebookdetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PricebookdetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
