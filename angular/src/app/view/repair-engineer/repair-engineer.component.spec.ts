import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RepairEngineerComponent } from './repair-engineer.component';

describe('RepairEngineerComponent', () => {
  let component: RepairEngineerComponent;
  let fixture: ComponentFixture<RepairEngineerComponent>;

  beforeEach(async(() => {
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
