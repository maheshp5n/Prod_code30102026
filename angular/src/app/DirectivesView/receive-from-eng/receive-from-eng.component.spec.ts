import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReceiveFromEngComponent } from './receive-from-eng.component';

describe('ReceiveFromEngComponent', () => {
  let component: ReceiveFromEngComponent;
  let fixture: ComponentFixture<ReceiveFromEngComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReceiveFromEngComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReceiveFromEngComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
