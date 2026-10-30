import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatServiceresourceComponent } from './bat-serviceresource.component';

describe('BatServiceresourceComponent', () => {
  let component: BatServiceresourceComponent;
  let fixture: ComponentFixture<BatServiceresourceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BatServiceresourceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatServiceresourceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
