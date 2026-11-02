import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { RepairEngineerComponent } from './repair-engineer.component';

describe('RepairEngineerComponent', () => {
  let component: RepairEngineerComponent;
  let fixture: ComponentFixture<RepairEngineerComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ RepairEngineerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RepairEngineerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
