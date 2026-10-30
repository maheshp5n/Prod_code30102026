import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RepairEngineerDetailsComponent } from './repair-engineer-details.component';

describe('RepairEngineerDetailsComponent', () => {
  let component: RepairEngineerDetailsComponent;
  let fixture: ComponentFixture<RepairEngineerDetailsComponent>;

  beforeEach(async(() => {
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
