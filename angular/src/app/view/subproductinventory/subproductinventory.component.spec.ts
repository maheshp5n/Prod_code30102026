import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SubproductinventoryComponent } from './subproductinventory.component';

describe('SubproductinventoryComponent', () => {
  let component: SubproductinventoryComponent;
  let fixture: ComponentFixture<SubproductinventoryComponent>;

  beforeEach(async(() => {
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
