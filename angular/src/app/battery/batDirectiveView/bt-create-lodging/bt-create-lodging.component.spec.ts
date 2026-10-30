import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BtCreateLodgingComponent } from './bt-create-lodging.component';

describe('BtCreateLodgingComponent', () => {
  let component: BtCreateLodgingComponent;
  let fixture: ComponentFixture<BtCreateLodgingComponent>;

  beforeEach(async(() => {
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
