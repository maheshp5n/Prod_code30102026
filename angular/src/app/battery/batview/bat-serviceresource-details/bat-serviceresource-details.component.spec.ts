import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatServiceresourceDetailsComponent } from './bat-serviceresource-details.component';

describe('BatServiceresourceDetailsComponent', () => {
  let component: BatServiceresourceDetailsComponent;
  let fixture: ComponentFixture<BatServiceresourceDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BatServiceresourceDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatServiceresourceDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
