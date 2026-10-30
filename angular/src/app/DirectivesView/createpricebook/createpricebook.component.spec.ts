import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreatepricebookComponent } from './createpricebook.component';

describe('CreatepricebookComponent', () => {
  let component: CreatepricebookComponent;
  let fixture: ComponentFixture<CreatepricebookComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreatepricebookComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreatepricebookComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
