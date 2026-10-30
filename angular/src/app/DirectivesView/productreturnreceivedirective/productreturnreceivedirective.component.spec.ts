import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductreturnreceivedirectiveComponent } from './productreturnreceivedirective.component';

describe('ProductreturnreceivedirectiveComponent', () => {
  let component: ProductreturnreceivedirectiveComponent;
  let fixture: ComponentFixture<ProductreturnreceivedirectiveComponent>;

  beforeEach(async(() => {
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
