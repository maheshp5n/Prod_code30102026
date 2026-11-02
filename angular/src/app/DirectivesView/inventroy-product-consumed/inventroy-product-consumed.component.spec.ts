import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { InventroyProductConsumedComponent } from './inventroy-product-consumed.component';

describe('InventroyProductConsumedComponent', () => {
  let component: InventroyProductConsumedComponent;
  let fixture: ComponentFixture<InventroyProductConsumedComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ InventroyProductConsumedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InventroyProductConsumedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
