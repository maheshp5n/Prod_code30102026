import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatUserregistrationComponent } from './bat-userregistration.component';

describe('BatUserregistrationComponent', () => {
  let component: BatUserregistrationComponent;
  let fixture: ComponentFixture<BatUserregistrationComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatUserregistrationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatUserregistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
