import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CreateIssuedcomponentComponent } from './create-issuedcomponent.component';

describe('CreateIssuedcomponentComponent', () => {
  let component: CreateIssuedcomponentComponent;
  let fixture: ComponentFixture<CreateIssuedcomponentComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateIssuedcomponentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateIssuedcomponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
