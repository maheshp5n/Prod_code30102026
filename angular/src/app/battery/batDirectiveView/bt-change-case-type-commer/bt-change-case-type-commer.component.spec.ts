import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BtChangeCaseTypeCommerComponent } from './bt-change-case-type-commer.component';

describe('BtChangeCaseTypeCommerComponent', () => {
  let component: BtChangeCaseTypeCommerComponent;
  let fixture: ComponentFixture<BtChangeCaseTypeCommerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BtChangeCaseTypeCommerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtChangeCaseTypeCommerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
