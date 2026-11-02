import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CreateSubProductInventoryComponent } from './create-sub-product-inventory.component';

describe('CreateSubProductInventoryComponent', () => {
  let component: CreateSubProductInventoryComponent;
  let fixture: ComponentFixture<CreateSubProductInventoryComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateSubProductInventoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateSubProductInventoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
