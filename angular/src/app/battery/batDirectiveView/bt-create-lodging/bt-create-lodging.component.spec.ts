import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BtCreateLodgingComponent } from './bt-create-lodging.component';

describe('BtCreateLodgingComponent', () => {
  let component: BtCreateLodgingComponent;
  let fixture: ComponentFixture<BtCreateLodgingComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BtCreateLodgingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtCreateLodgingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
