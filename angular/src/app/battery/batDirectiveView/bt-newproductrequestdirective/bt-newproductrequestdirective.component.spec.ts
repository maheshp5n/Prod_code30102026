import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BtNewproductrequestdirectiveComponent } from './bt-newproductrequestdirective.component';

describe('BtNewproductrequestdirectiveComponent', () => {
  let component: BtNewproductrequestdirectiveComponent;
  let fixture: ComponentFixture<BtNewproductrequestdirectiveComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BtNewproductrequestdirectiveComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtNewproductrequestdirectiveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
