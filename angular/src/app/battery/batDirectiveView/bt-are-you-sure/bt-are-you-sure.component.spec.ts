import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BtAreYouSureComponent } from './bt-are-you-sure.component';

describe('BtAreYouSureComponent', () => {
  let component: BtAreYouSureComponent;
  let fixture: ComponentFixture<BtAreYouSureComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BtAreYouSureComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtAreYouSureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
