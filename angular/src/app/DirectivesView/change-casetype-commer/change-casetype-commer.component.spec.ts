import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ChangeCasetypeCommerComponent } from './change-casetype-commer.component';

describe('ChangeCasetypeCommerComponent', () => {
  let component: ChangeCasetypeCommerComponent;
  let fixture: ComponentFixture<ChangeCasetypeCommerComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ChangeCasetypeCommerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChangeCasetypeCommerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
