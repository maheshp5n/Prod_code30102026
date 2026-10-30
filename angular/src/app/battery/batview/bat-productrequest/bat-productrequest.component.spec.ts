import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatProductrequestComponent } from './bat-productrequest.component';

describe('BatProductrequestComponent', () => {
  let component: BatProductrequestComponent;
  let fixture: ComponentFixture<BatProductrequestComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BatProductrequestComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatProductrequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
