import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateNewdeliverychallanComponent } from './create-new-deliverychallan.component';

describe('CreateNewdeliverychallanComponent', () => {
  let component: CreateNewdeliverychallanComponent;
  let fixture: ComponentFixture<CreateNewdeliverychallanComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateNewdeliverychallanComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateNewdeliverychallanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
