import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BulkuploadComponent } from './bulkupload.component';

describe('BulkuploadComponent', () => {
  let component: BulkuploadComponent;
  let fixture: ComponentFixture<BulkuploadComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BulkuploadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BulkuploadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
