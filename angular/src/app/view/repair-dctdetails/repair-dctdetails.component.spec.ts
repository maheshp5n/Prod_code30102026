import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RepairDCTDetailsComponent } from './repair-dctdetails.component';

describe('RepairDCTDetailsComponent', () => {
  let component: RepairDCTDetailsComponent;
  let fixture: ComponentFixture<RepairDCTDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RepairDCTDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RepairDCTDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
