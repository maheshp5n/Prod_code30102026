import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BTCreatecaseComponent } from './btcreatecase.component';

describe('BTCreatecaseComponent', () => {
  let component: BTCreatecaseComponent;
  let fixture: ComponentFixture<BTCreatecaseComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BTCreatecaseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BTCreatecaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
