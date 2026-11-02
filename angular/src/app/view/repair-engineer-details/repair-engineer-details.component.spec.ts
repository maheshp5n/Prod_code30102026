import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { RepairEngineerDetailsComponent } from './repair-engineer-details.component';

describe('RepairEngineerDetailsComponent', () => {
  let component: RepairEngineerDetailsComponent;
  let fixture: ComponentFixture<RepairEngineerDetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ RepairEngineerDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RepairEngineerDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
