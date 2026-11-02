import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatContactsComponent } from './bat-contacts.component';

describe('BatContactsComponent', () => {
  let component: BatContactsComponent;
  let fixture: ComponentFixture<BatContactsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatContactsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatContactsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
