import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateSubProductInventoryComponent } from './create-sub-product-inventory.component';

describe('CreateSubProductInventoryComponent', () => {
  let component: CreateSubProductInventoryComponent;
  let fixture: ComponentFixture<CreateSubProductInventoryComponent>;

  beforeEach(async(() => {
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
