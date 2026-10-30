import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddProductConsumedComponent } from './add-product-consumed.component';

describe('AddProductConsumedComponent', () => {
  let component: AddProductConsumedComponent;
  let fixture: ComponentFixture<AddProductConsumedComponent>;

  beforeEach(async(() => {
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
