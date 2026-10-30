import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BtCreateassetComponent } from './bt-createasset.component';

describe('BtCreateassetComponent', () => {
  let component: BtCreateassetComponent;
  let fixture: ComponentFixture<BtCreateassetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BtCreateassetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtCreateassetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
