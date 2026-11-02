import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { SubproductinventorydetailsComponent } from './subproductinventorydetails.component';

describe('SubproductinventorydetailsComponent', () => {
  let component: SubproductinventorydetailsComponent;
  let fixture: ComponentFixture<SubproductinventorydetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ SubproductinventorydetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubproductinventorydetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
