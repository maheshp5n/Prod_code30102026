import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ProductreturnreceivedirectiveComponent } from './productreturnreceivedirective.component';

describe('ProductreturnreceivedirectiveComponent', () => {
  let component: ProductreturnreceivedirectiveComponent;
  let fixture: ComponentFixture<ProductreturnreceivedirectiveComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ProductreturnreceivedirectiveComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductreturnreceivedirectiveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
