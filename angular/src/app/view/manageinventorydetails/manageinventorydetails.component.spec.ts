import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ManageinventorydetailsComponent } from './manageinventorydetails.component';

describe('ManageinventorydetailsComponent', () => {
  let component: ManageinventorydetailsComponent;
  let fixture: ComponentFixture<ManageinventorydetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ManageinventorydetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageinventorydetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
