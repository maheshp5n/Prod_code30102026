import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { SubproductinventoryComponent } from './subproductinventory.component';

describe('SubproductinventoryComponent', () => {
  let component: SubproductinventoryComponent;
  let fixture: ComponentFixture<SubproductinventoryComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ SubproductinventoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubproductinventoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
