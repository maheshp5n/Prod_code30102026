import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SystemconfigurationparametersComponent } from './systemconfigurationparameters.component';

describe('SystemconfigurationparametersComponent', () => {
  let component: SystemconfigurationparametersComponent;
  let fixture: ComponentFixture<SystemconfigurationparametersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SystemconfigurationparametersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SystemconfigurationparametersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
