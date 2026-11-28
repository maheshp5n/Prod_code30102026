import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ServiceresourceComponent } from './serviceresource.component';

describe('ServiceresourceComponent', () => {
  let component: ServiceresourceComponent;
  let fixture: ComponentFixture<ServiceresourceComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ServiceresourceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ServiceresourceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
