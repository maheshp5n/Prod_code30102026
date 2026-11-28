import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { AddProductConsumedComponent } from './add-product-consumed.component';

describe('AddProductConsumedComponent', () => {
  let component: AddProductConsumedComponent;
  let fixture: ComponentFixture<AddProductConsumedComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ AddProductConsumedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddProductConsumedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
