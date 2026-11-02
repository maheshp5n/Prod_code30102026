import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BtCreateLegalentityComponent } from './bt-create-legalentity.component';

describe('BtCreateLegalentityComponent', () => {
  let component: BtCreateLegalentityComponent;
  let fixture: ComponentFixture<BtCreateLegalentityComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BtCreateLegalentityComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtCreateLegalentityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
