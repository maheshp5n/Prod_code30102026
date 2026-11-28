import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CreateNewQuoteComponent } from './create-new-quote.component';

describe('CreateNewQuoteComponent', () => {
  let component: CreateNewQuoteComponent;
  let fixture: ComponentFixture<CreateNewQuoteComponent>;

  beforeEach(waitForAsync(() => {
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
