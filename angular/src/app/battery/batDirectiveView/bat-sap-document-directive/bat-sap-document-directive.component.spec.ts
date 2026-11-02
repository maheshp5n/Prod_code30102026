import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatSapDocumentDirectiveComponent } from './bat-sap-document-directive.component';

describe('BatSapDocumentDirectiveComponent', () => {
  let component: BatSapDocumentDirectiveComponent;
  let fixture: ComponentFixture<BatSapDocumentDirectiveComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatSapDocumentDirectiveComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatSapDocumentDirectiveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
