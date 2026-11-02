import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BtCreateserviceresourceComponent } from './bt-createserviceresource.component';

describe('BtCreateserviceresourceComponent', () => {
  let component: BtCreateserviceresourceComponent;
  let fixture: ComponentFixture<BtCreateserviceresourceComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BtCreateserviceresourceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtCreateserviceresourceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
