import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CreateinvoiceComponent } from './createinvoice.component';

describe('CreateinvoiceComponent', () => {
  let component: CreateinvoiceComponent;
  let fixture: ComponentFixture<CreateinvoiceComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateinvoiceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateinvoiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
