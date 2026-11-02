import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { RepairDCTComponent } from './repair-dct.component';

describe('RepairDCTComponent', () => {
  let component: RepairDCTComponent;
  let fixture: ComponentFixture<RepairDCTComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ RepairDCTComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RepairDCTComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
