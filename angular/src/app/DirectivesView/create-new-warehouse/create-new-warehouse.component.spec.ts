import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CreateNewWarehouseComponent } from './create-new-warehouse.component';

describe('CreateNewWarehouseComponent', () => {
  let component: CreateNewWarehouseComponent;
  let fixture: ComponentFixture<CreateNewWarehouseComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateNewWarehouseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateNewWarehouseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
