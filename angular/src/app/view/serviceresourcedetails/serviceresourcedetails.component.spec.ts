import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceresourcedetailsComponent } from './serviceresourcedetails.component';

describe('ServiceresourcedetailsComponent', () => {
  let component: ServiceresourcedetailsComponent;
  let fixture: ComponentFixture<ServiceresourcedetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ServiceresourcedetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ServiceresourcedetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
