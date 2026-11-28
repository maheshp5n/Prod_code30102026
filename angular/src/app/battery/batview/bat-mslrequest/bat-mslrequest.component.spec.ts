import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatMslrequestComponent } from './bat-mslrequest.component';

describe('BatMslrequestComponent', () => {
  let component: BatMslrequestComponent;
  let fixture: ComponentFixture<BatMslrequestComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatMslrequestComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatMslrequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
