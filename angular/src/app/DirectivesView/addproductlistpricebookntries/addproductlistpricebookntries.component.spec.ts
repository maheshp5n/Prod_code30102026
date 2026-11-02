import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { AddproductlistpricebookntriesComponent } from './addproductlistpricebookntries.component';

describe('AddproductlistpricebookntriesComponent', () => {
  let component: AddproductlistpricebookntriesComponent;
  let fixture: ComponentFixture<AddproductlistpricebookntriesComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ AddproductlistpricebookntriesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddproductlistpricebookntriesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
