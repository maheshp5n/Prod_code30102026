import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewbatproducttransferComponent } from './newbatproducttransfer.component';

describe('NewbatproducttransferComponent', () => {
  let component: NewbatproducttransferComponent;
  let fixture: ComponentFixture<NewbatproducttransferComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewbatproducttransferComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewbatproducttransferComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
