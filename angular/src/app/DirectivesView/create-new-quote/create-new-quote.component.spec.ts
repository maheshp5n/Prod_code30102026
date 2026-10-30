import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateNewQuoteComponent } from './create-new-quote.component';

describe('CreateNewQuoteComponent', () => {
  let component: CreateNewQuoteComponent;
  let fixture: ComponentFixture<CreateNewQuoteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateNewQuoteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateNewQuoteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
