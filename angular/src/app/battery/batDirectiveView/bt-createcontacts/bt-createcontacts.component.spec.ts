import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BtCreatecontactsComponent } from './bt-createcontacts.component';

describe('BtCreatecontactsComponent', () => {
  let component: BtCreatecontactsComponent;
  let fixture: ComponentFixture<BtCreatecontactsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BtCreatecontactsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtCreatecontactsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
