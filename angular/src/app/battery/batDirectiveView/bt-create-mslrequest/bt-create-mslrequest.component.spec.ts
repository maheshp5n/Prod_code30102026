import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BtCreateMslrequestComponent } from './bt-create-mslrequest.component';

describe('BtCreateMslrequestComponent', () => {
  let component: BtCreateMslrequestComponent;
  let fixture: ComponentFixture<BtCreateMslrequestComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BtCreateMslrequestComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtCreateMslrequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
