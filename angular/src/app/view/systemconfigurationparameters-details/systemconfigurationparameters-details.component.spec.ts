import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SystemconfigurationparametersDetailsComponent } from './systemconfigurationparameters-details.component';

describe('SystemconfigurationparametersDetailsComponent', () => {
  let component: SystemconfigurationparametersDetailsComponent;
  let fixture: ComponentFixture<SystemconfigurationparametersDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SystemconfigurationparametersDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SystemconfigurationparametersDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
