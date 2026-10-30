import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewserviceorderComponent } from './newserviceorder.component';

describe('NewserviceorderComponent', () => {
  let component: NewserviceorderComponent;
  let fixture: ComponentFixture<NewserviceorderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewserviceorderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewserviceorderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
