import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddproductlistpricebookntriesComponent } from './addproductlistpricebookntries.component';

describe('AddproductlistpricebookntriesComponent', () => {
  let component: AddproductlistpricebookntriesComponent;
  let fixture: ComponentFixture<AddproductlistpricebookntriesComponent>;

  beforeEach(async(() => {
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
