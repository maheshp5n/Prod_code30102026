import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { EditvendorinvoiceComponent } from './editvendorinvoice.component';

describe('EditvendorinvoiceComponent', () => {
  let component: EditvendorinvoiceComponent;
  let fixture: ComponentFixture<EditvendorinvoiceComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ EditvendorinvoiceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditvendorinvoiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
