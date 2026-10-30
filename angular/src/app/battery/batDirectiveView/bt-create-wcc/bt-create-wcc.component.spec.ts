import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BtCreateWccComponent } from './bt-create-wcc.component';

describe('BtCreateWccComponent', () => {
  let component: BtCreateWccComponent;
  let fixture: ComponentFixture<BtCreateWccComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BtCreateWccComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtCreateWccComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
