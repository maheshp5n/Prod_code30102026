import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatEditGRNComponent } from './bat-edit-grn.component';

describe('BatEditGRNComponent', () => {
  let component: BatEditGRNComponent;
  let fixture: ComponentFixture<BatEditGRNComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatEditGRNComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatEditGRNComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
