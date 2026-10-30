import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatContactsDetailsComponent } from './bat-contacts-details.component';

describe('BatContactsDetailsComponent', () => {
  let component: BatContactsDetailsComponent;
  let fixture: ComponentFixture<BatContactsDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BatContactsDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatContactsDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
