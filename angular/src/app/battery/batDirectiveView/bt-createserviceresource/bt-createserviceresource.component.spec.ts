import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BtCreateserviceresourceComponent } from './bt-createserviceresource.component';

describe('BtCreateserviceresourceComponent', () => {
  let component: BtCreateserviceresourceComponent;
  let fixture: ComponentFixture<BtCreateserviceresourceComponent>;

  beforeEach(async(() => {
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
