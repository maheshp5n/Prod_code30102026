import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MslrequestComponent } from './mslrequest.component';

describe('MslrequestComponent', () => {
  let component: MslrequestComponent;
  let fixture: ComponentFixture<MslrequestComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MslrequestComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MslrequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
