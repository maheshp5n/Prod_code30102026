import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductreturnorderdirectiveComponent } from './productreturnorderdirective.component';

describe('ProductreturnorderdirectiveComponent', () => {
  let component: ProductreturnorderdirectiveComponent;
  let fixture: ComponentFixture<ProductreturnorderdirectiveComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProductreturnorderdirectiveComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductreturnorderdirectiveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
