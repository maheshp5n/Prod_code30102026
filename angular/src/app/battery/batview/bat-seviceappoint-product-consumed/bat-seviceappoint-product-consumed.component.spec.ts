import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatSeviceappointProductConsumedComponent } from './bat-seviceappoint-product-consumed.component';

describe('BatSeviceappointProductConsumedComponent', () => {
  let component: BatSeviceappointProductConsumedComponent;
  let fixture: ComponentFixture<BatSeviceappointProductConsumedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BatSeviceappointProductConsumedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatSeviceappointProductConsumedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
